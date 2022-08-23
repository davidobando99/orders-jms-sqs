package com.epam.javaprogram.jmssqs.service.jms;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.epam.javaprogram.jmssqs.pojos.ProcessedGoodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;

@Service
public class ProcessedOrderReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessedOrderReceiver.class);

    private final String S3_BUCKET_NAME = "order-final-logs";

    private final String LOGS_FILE_NAME = "orders_final_logs";

    @Autowired
    AmazonS3 amazonS3;

    @JmsListener(destination = "processed-orders-queue")
    public void receive(@Payload ProcessedGoodOrder processedGoodOrder) throws IOException {
        LOGGER.info("Order Processed received!");
        LOGGER.info("Order Processed is == " + processedGoodOrder);
        publishToS3(processedGoodOrder,new Date().toString());
    }

    public void publishToS3(ProcessedGoodOrder processedGoodOrder, String logDate) throws IOException {
        try {
            LOGGER.info("SENDING processed good order {} to S3",processedGoodOrder.getGoodOrder().getGoodOrderId());
            amazonS3.putObject(S3_BUCKET_NAME, LOGS_FILE_NAME, createFinalOrderLog(processedGoodOrder.toString(),logDate ));
        } catch (AmazonServiceException e) {
            LOGGER.error(e.getErrorMessage());
        }
    }

    private String createFinalOrderLog(String orderLog, String dateLog) throws IOException {
        return readS3FileLogs() + dateLog + "\t" + orderLog + "\n";
    }

    private String readS3FileLogs() throws IOException {
        if (doesLogFileNotExists()) {
            createLogFile();
        }
        InputStream logContent = amazonS3.getObject(S3_BUCKET_NAME, LOGS_FILE_NAME).getObjectContent();
        return new String(logContent.readAllBytes());
    }

    private void createLogFile() throws IOException {
        Path tempFile = Files.createTempFile(LOGS_FILE_NAME, "txt");
        amazonS3.putObject(S3_BUCKET_NAME, LOGS_FILE_NAME, tempFile.toFile());
    }

    private boolean doesLogFileNotExists() {
        return !amazonS3.doesObjectExist(S3_BUCKET_NAME, LOGS_FILE_NAME);
    }


}
