package com.epam.javaprogram.jmssqs.pojos;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Good {

    private String goodId;
    private String goodsType;
    private int volume;
    private int price;


    @JsonCreator
    public Good(@JsonProperty("goodId") String goodId,@JsonProperty("goodsType") String goodsType,@JsonProperty("volume") int volume, @JsonProperty("price") int price) {
        this.goodId = goodId;
        this.goodsType = goodsType;
        this.volume = volume;
        this.price = price;
    }

    public String getGoodId() {
        return goodId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public int getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodId='" + goodId + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", volume='" + volume + '\'' +
                ", price=" + price +
                '}';
    }
}
