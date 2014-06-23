package com.dmcliver.springjms;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class AppConfig {

	@Bean
	public ActiveMQQueue testQueueRef(){		
		return new ActiveMQQueue("testQueue");
	}
	
	@Bean
	public ActiveMQConnectionFactory jmsConnectionFactory(){
		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("vm://localhost");
		return connectionFactory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate(){
		
		JmsTemplate tmplte = new JmsTemplate();
		tmplte.setConnectionFactory(jmsConnectionFactory());
		tmplte.setDefaultDestination(testQueueRef());
		return tmplte;
	}
}
