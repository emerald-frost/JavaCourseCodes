package io.kimmking.kmq.core;

import java.time.Duration;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.SneakyThrows;

public final class Kmq {

	public Kmq(String topic, int capacity) {
		this.topic = topic;
		this.capacity = capacity;
		this.queue = new KmqMessage[capacity];

		lock = new ReentrantLock(false);
		notEmpty = lock.newCondition();
	}

	private String topic;

	private int capacity;

	private KmqMessage[] queue;

	private int nextTake = 0;

	private int nextPut = 0;

	private int count = 0;

	private ReentrantLock lock;

	private Condition notEmpty;

	private void enqueue(KmqMessage message) {
		this.queue[this.nextPut] = message;
		this.nextPut++;
		if (nextPut == capacity) {
			nextPut = 0;
		}
		this.count++;
		notEmpty.signal();
	}

	private KmqMessage dequeue() {
		final KmqMessage message = this.queue[this.nextTake];
		this.queue[this.nextTake] = null;
		this.nextTake++;
		if (nextTake == capacity) {
			nextTake = 0;
		}
		this.count--;
		return message;
	}

	public boolean send(KmqMessage message) {
		lock.lock();
		try {
			if (count == capacity) {
				System.out.println("写满了");
				return false;
			}
			else {
				enqueue(message);
				return true;
			}
		}
		finally {
			lock.unlock();
		}
	}

	public KmqMessage poll() {
		lock.lock();
		try {
			if (count == 0) {
				return null;
			}
			else {
				return dequeue();
			}
		}
		finally {
			lock.unlock();
		}
	}

	@SneakyThrows
	public KmqMessage poll(long timeout) {
		lock.lockInterruptibly();
		try {
			long nanos = Duration.ofMillis(timeout).toNanos();
			while (count == 0) {
				if (nanos <= 0) {
					return null;
				}
				else {
					nanos = notEmpty.awaitNanos(nanos);
				}
			}
			return dequeue();
		}
		finally {
			lock.unlock();
		}
	}

}
