package com.epam.javaprogram.jmssqs.service.jms;

import com.amazonaws.services.sqs.AmazonSQS;
import com.epam.javaprogram.jmssqs.pojos.GoodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;


@Service
public class GoodOrderService {

    private static final String ORDERS_QUEUE = "orders-queue";

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private AmazonSQS amazonSQSClient;

    public void send(GoodOrder goodOrder) throws JMSException {

        jmsTemplate.convertAndSend(ORDERS_QUEUE, goodOrder, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("goodsType", goodOrder.getGoodsType());
                return message;
            }
        });

    }
}
