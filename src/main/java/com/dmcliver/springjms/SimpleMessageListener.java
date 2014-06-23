package com.dmcliver.springjms;

import static java.lang.System.out;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

@Component
public class SimpleMessageListener implements MessageListener{

	public void onMessage(Message mess) {

		TextMessage txt = (TextMessage)mess;
		try {
			out.println("Message: " + txt.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
