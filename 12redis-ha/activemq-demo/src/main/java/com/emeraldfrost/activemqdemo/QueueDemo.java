package com.emeraldfrost.activemqdemo;

import java.time.LocalDateTime;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueDemo {

	public static void main(String[] args) throws JMSException, InterruptedException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
		Connection connection =  connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// queue
		Destination destination = session.createQueue("activemqdemo.queue");
		// 生产者
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// 消费者
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(message -> {
			try {
				if (message != null) {
					TextMessage textMessage = (TextMessage) message;
					System.out.println("consumer received: " + textMessage.getText());
				}
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		});

		connection.start();

		// 发消息
		String text = "test queue "+ LocalDateTime.now();
		TextMessage message = session.createTextMessage(text);
		producer.send(message);

		Thread.sleep(2_000L);

		consumer.close();
		producer.close();
		session.close();
		connection.close();
	}
}
