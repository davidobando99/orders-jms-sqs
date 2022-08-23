package com.epam.javaprogram.jmssqs;
import com.amazonaws.services.sqs.AmazonSQS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    AmazonSQS amazonSQS;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }







}