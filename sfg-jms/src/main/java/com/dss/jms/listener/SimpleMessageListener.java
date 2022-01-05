package com.dss.jms.listener;

import com.dss.jms.config.JmsConfig;
import com.dss.jms.model.SimpleMessage;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@AllArgsConstructor
@Component
public class SimpleMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload SimpleMessage simpleMessage, @Headers MessageHeaders headers, Message message) {

        //System.out.println("I got a Message!!!");

        //System.out.println(simpleMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenFromHellos(@Payload SimpleMessage simpleMessage, @Headers MessageHeaders headers, Message message) throws JMSException {

        SimpleMessage payloadMessage = SimpleMessage.builder().id(UUID.randomUUID()).message("World!!").build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMessage);

    }

}
