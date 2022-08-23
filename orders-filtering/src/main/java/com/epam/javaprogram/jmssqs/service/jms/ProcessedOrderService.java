package com.epam.javaprogram.jmssqs.service.jms;

import com.amazonaws.services.sqs.AmazonSQS;
import com.epam.javaprogram.jmssqs.pojos.GoodOrder;
import com.epam.javaprogram.jmssqs.pojos.ProcessedGoodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProcessedOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessedOrderService.class);

    private static final String PROCESSED_ORDERS_QUEUE = "processed-orders-queue";

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private AmazonSQS amazonSQSClient;

    public JmsResponse<Message<ProcessedGoodOrder>> processOrder(GoodOrder goodOrder, String orderMessage){
        return JmsResponse.forQueue(buildGoodOrder(goodOrder,orderMessage), PROCESSED_ORDERS_QUEUE);

    }


    private Message<ProcessedGoodOrder> buildGoodOrder(GoodOrder goodOrder, String message){
        LOGGER.info("CREATING NEW PROCESSED ORDER");
        return MessageBuilder
                .withPayload(new ProcessedGoodOrder(goodOrder, message))
                .build();
    }
}
