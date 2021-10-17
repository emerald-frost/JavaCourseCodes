package com.github.emeraldfrost.usejuc;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.useFutureTask();
        main.useFutureAndPool();
        main.useSemaphore();
        main.useCountDownLatch();
        main.useCyclicBarrier();
        main.useCompletableFuture();
        main.useBlockingQueue();
        main.useReentrantLock();
        main.useJoin();
    }

    /**
     * 需要执行的业务
     */
    private String biz(String methodName) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(50L);
        return methodName + ": ok";
    }

    private void useFutureTask() throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(() -> biz("useFutureTask"));
        new Thread(futureTask).start();
        System.out.println(futureTask.get(100L, TimeUnit.MILLISECONDS));
    }

    private void useFutureAndPool() throws Exception {
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return biz("useFuture");
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<String> future = pool.submit(task);
        System.out.println(future.get(100L, TimeUnit.MILLISECONDS));
    }

    private void useSemaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        final String[] s = new String[1];
        new Thread(() -> {
            try {
                semaphore.acquire(1);
                s[0] = biz("useSemaphore");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(2);
            }
        }).start();
        semaphore.acquire(2);
        System.out.println(s[0]);
    }

    private void useCountDownLatch() throws InterruptedException {
        final String[] s = new String[1];
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                s[0] = biz("useCountDownLatch");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();
        latch.await(100L, TimeUnit.MILLISECONDS);
        System.out.println(s[0]);
    }

    private void useCyclicBarrier() throws Exception {
        final String[] s = new String[1];
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                s[0] = biz("useCyclicBarrier");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        cyclicBarrier.await();
        System.out.println(s[0]);
    }

    private void useCompletableFuture() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return biz("useCompletableFuture");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        System.out.println(completableFuture.join());
    }

    private void useBlockingQueue() throws InterruptedException {
        final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        new Thread(() -> {
            try {
                queue.put(biz("useBlockingQueue"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println(queue.take());
    }

    private void useReentrantLock() throws InterruptedException {
        final String[] s = new String[1];
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                s[0] = biz("useReentrantLock");
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
        try {
            lock.lock();
            condition.await();
            System.out.println(s[0]);
        } finally {
            lock.unlock();
        }

    }

    private void useJoin() throws InterruptedException {
        final String[] s = new String[1];
        Thread thread = new Thread(() -> {
            try {
                s[0] = biz("useJoin");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
        System.out.println(s[0]);
    }
}
