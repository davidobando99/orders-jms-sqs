package com.epam.javaprogram.jmssqs.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;


@EnableJms
@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter jacksonJmsMessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }


    @Bean
    public SQSConnectionFactory connectionFactory() {
        DefaultAWSCredentialsProviderChain credentials = new DefaultAWSCredentialsProviderChain();

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                credentials.getCredentials().getAWSAccessKeyId(),
                credentials.getCredentials().getAWSSecretKey());
        return new SQSConnectionFactory(new ProviderConfiguration(),
                SqsClient.builder()
                        .region(Region.US_EAST_1)
                        .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                        .build());
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setSessionTransacted(false);
        return jmsTemplate;

    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }
}
