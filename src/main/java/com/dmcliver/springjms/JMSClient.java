package com.dmcliver.springjms;

import static java.lang.System.in;
import static java.lang.System.out;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author dmcliver - me
 * @since 22 june 2014
 */
public class JMSClient {

	/**
	 * This is `real` spring JMS example i.e. not spring-integration JMS (which is cool also)
	 * @param args
	 * @throws InterruptedException 
	 * @throws JMSException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, JMSException, IOException {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring-context.xml");

		JmsTemplate jmsTemplate = usingJmsTemplate(ctx);
		usingSpecificDest(jmsTemplate, ctx);
		usingJmsApi(ctx, jmsTemplate.getConnectionFactory());
		
		out.println("Please any key to exit");
		in.read();
		ctx.close();
	}

	private static JmsTemplate usingJmsTemplate(ClassPathXmlApplicationContext ctx) {
		
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		jmsTemplate.convertAndSend("Default Spring JMS Template Message");
		return jmsTemplate;
	}
	
	private static void usingSpecificDest(JmsTemplate jmsTemplate, ApplicationContext ctx) {
		
		Destination  dest = (Destination)ctx.getBean("testQueueRef");
		jmsTemplate.convertAndSend(dest, "Specific Spring JMS Template Message");
	}

	private static void usingJmsApi(ApplicationContext ctx, ConnectionFactory connectionFactory) throws JMSException{
		
		Connection connection = null;
		try {
			
			connection = connectionFactory.createConnection();
			Destination dest = (Destination) ctx.getBean("testQueueRef");
			Session session = connection.createSession(false, AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(dest);
			TextMessage txtMessage = new ActiveMQTextMessage();
			txtMessage.setText("Standard JMS Template Message");
			producer.send(dest, txtMessage);
		} 
		finally {
			
			if(connection != null)
				connection.close();
		}
	}
}

