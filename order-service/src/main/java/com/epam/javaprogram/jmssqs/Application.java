package com.epam.javaprogram.jmssqs;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.epam.javaprogram.jmssqs.pojos.Good;
import com.epam.javaprogram.jmssqs.pojos.GoodOrder;
import com.epam.javaprogram.jmssqs.service.jms.GoodOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import java.io.DataInput;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    GoodOrderService orderService;

    @Autowired
    AmazonSQS amazonSQS;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl("https://sqs.us-east-1.amazonaws.com/221487488153/whenwhen")
                .withMessageBody("hello world")
                .withDelaySeconds(5);
    }

    @Override
    public void run(String... args) throws JMSException {
        sendOrder();
    }


    public void sendOrder() throws JMSException {
        amazonSQS.createQueue(new CreateQueueRequest("orders-queue"));
        amazonSQS.createQueue(new CreateQueueRequest("processed-orders-queue"));
        //var good = new Good("1","Liquid",10,1000);
        ObjectMapper mapper = new ObjectMapper();


        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        //URL resource = getClass().getClassLoader().getResource("com/epam/javaprogram/jmssqs/orders.json");
        try (FileReader reader = new FileReader("src/main/resources/orders.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject goodJson = (JSONObject) obj;
            GoodOrder goodsOrder = mapper.readValue(goodJson.toJSONString(), GoodOrder.class);
            orderService.send(goodsOrder);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }




}