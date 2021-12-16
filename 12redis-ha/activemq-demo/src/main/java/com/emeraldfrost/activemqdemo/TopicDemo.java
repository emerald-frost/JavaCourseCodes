package com.emeraldfrost.activemqdemo;

import java.time.LocalDateTime;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicDemo {

	public static void main(String[] args) throws JMSException, InterruptedException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// topic
		final Topic topic = session.createTopic("activemqdemo.topic");
		// 生产者
		MessageProducer producer = session.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// 消费者
		MessageConsumer consumer1 = session.createConsumer(topic);
		consumer1.setMessageListener(message -> {
			try {
				if (message != null) {
					TextMessage textMessage = (TextMessage) message;
					System.out.println("consumer1 received: " + textMessage.getText());
				}
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		});
		MessageConsumer consumer2 = session.createConsumer(topic);
		consumer2.setMessageListener(message -> {
			try {
				if (message != null) {
					TextMessage textMessage = (TextMessage) message;
					System.out.println("consumer2 received: " + textMessage.getText());
				}
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		});

		connection.start();

		// 发消息
		String text = "test topic " + LocalDateTime.now();
		TextMessage message = session.createTextMessage(text);
		producer.send(message);

		Thread.sleep(2_000L);

		consumer2.close();
		consumer1.close();
		producer.close();
		session.close();
		connection.close();
	}
}
