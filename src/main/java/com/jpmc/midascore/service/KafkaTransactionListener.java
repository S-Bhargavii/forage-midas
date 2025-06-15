package com.jpmc.midascore.service;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaTransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTransactionListener.class);

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${general.kafka-topic}")
    public void listen(Transaction transaction){
        try{
            logger.info("Recieved transaction : {}", transaction.toString());
        } catch (Exception e){
            logger.error("An error occured while deserialising the transaction");
        }
    }

}
