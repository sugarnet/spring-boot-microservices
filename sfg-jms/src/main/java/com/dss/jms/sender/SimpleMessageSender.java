package com.dss.jms.sender;

import com.dss.jms.config.JmsConfig;
import com.dss.jms.model.SimpleMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SimpleMessageSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        System.out.println("Sending Message...");

        SimpleMessage simpleMessage = SimpleMessage.builder().id(UUID.randomUUID()).message("Hello World !!").build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, simpleMessage);

        System.out.println("Message Sent!");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {

        System.out.println("Sending Message...");

        SimpleMessage simpleMessage = SimpleMessage.builder().id(UUID.randomUUID()).message("Hello!!").build();

        Message message = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                try {
                    Message message = session.createTextMessage(objectMapper.writeValueAsString(simpleMessage));
                    message.setStringProperty("_type", "com.dss.jms.model.SimpleMessage");

                    System.out.println("Sending Hello...");

                    return message;

                } catch (JsonProcessingException e) {
                    throw new JMSException("Boom!!");
                }
            }
        });

        System.out.println(message.getBody(String.class));
    }
}
