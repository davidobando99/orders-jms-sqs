package com.epam.javaprogram.jmssqs.pojos;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GoodOrder {

    private String goodOrderId;
    private String customer;

    private String goodsType;
    private int volumeLiquids;
    private int amountItems;
    private double orderTotal;

    @JsonCreator
    public GoodOrder(@JsonProperty("goodOrderId") String goodOrderId, @JsonProperty("customer") String customer, @JsonProperty("goodsType") String goodsType, @JsonProperty("volumeLiquids") int volumeLiquids, @JsonProperty("amountItems") int amountItems,@JsonProperty("orderTotal")  double orderTotal) {
        this.goodOrderId = goodOrderId;
        this.customer = customer;
        this.goodsType =  goodsType;
        this.volumeLiquids = volumeLiquids;
        this.amountItems = amountItems;
        this.orderTotal = orderTotal;
    }

    public String getGoodOrderId() {
        return goodOrderId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public int getVolumeLiquids() {
        return volumeLiquids;
    }

    public int getAmountItems() {
        return amountItems;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    @Override
    public String toString() {
        return "GoodOrder{" +
                "goodOrderId='" + goodOrderId + '\'' +
                ", customer='" + customer + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", volumeLiquids=" + volumeLiquids +
                ", amountItems=" + amountItems +
                ", orderTotal=" + orderTotal +
                '}';
    }
}

