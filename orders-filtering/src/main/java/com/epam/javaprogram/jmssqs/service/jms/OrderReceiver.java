package com.epam.javaprogram.jmssqs.service.jms;

import com.epam.javaprogram.jmssqs.pojos.Good;
import com.epam.javaprogram.jmssqs.pojos.GoodOrder;
import com.epam.javaprogram.jmssqs.pojos.ProcessedGoodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrderReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiver.class);
    @Autowired
    private ProcessedOrderService processedOrderService;

    @JmsListener(destination = "orders-queue")
    public JmsResponse<Message<ProcessedGoodOrder>> receive(@Payload GoodOrder goodsOrder, @Header(name = "goodsType") String goodsType) {
        LOGGER.info("Message received!");
        LOGGER.info("Message is == " + goodsOrder);
        LOGGER.info("Goods Type is {}", goodsType);
        if (goodsType.equals("liquid")) {
            if (goodsOrder.getVolumeLiquids() > 10) {
                return processedOrderService.processOrder(goodsOrder, "Order " + goodsOrder.getGoodOrderId() + " is rejected because is liquids are greater than 1O liters");
            }
        } else if (goodsOrder.getOrderTotal() > 10000.0) {
                return processedOrderService.processOrder(goodsOrder, "Order " + goodsOrder.getGoodOrderId() + " is rejected because order total is greater than 1O000 USD");
        }
        return processedOrderService.processOrder(goodsOrder, "Order " + goodsOrder.getGoodOrderId() + " is accepted");
    }


}
