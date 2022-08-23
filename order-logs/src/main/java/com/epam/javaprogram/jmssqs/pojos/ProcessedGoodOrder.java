package com.epam.javaprogram.jmssqs.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcessedGoodOrder {

    private GoodOrder goodOrder;

    private String orderFinalMessage;

    @JsonCreator
    public ProcessedGoodOrder(@JsonProperty("goodOrder") GoodOrder goodOrder,@JsonProperty("orderFinalMessage") String orderFinalMessage) {
        this.goodOrder = goodOrder;
        this.orderFinalMessage = orderFinalMessage;
    }


    public GoodOrder getGoodOrder() {
        return goodOrder;
    }

    public String getOrderFinalMessage() {
        return orderFinalMessage;
    }

    @Override
    public String toString() {
        return "ProcessedGoodOrder{" +
                "goodOrder=" + goodOrder +
                ", orderFinalMessage='" + orderFinalMessage + '\'' +
                '}';
    }
}
