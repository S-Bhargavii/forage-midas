package com.jpmc.midascore.service;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaTransactionListener {
    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${general.kafka-topic}")
    public void listen(Transaction transaction){
        try{
            System.out.println(transaction.getAmount());
        } catch (Exception e){
            System.out.println("Error in deserailising transaction");
        }
    }

}
