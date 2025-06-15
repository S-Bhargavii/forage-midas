package com.jpmc.midascore.service;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class KafkaTransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTransactionListener.class);

//    using the autowired annotation means we are telling the
//    application context or the bean factory to inject the object
//    from the bean container it has
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IncentiveService incentiveService;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${general.kafka-topic}")
    public void listen(Transaction transaction){
        try{
            logger.info("Recieved transaction : {}", transaction.toString());
            processTransaction(transaction);
//            printAllUsers();
        } catch (Exception e){
            logger.error("An error occured while deserialising the transaction : {}",e.toString());
        }
    }

//    public void printAllUsers() {
//        List<UserRecord> users = (List<UserRecord>) userRepository.findAll();
//        for (UserRecord user : users) {
//            System.out.println(user); // Ensure UserRecord has a meaningful toString() method
//        }
//    }

    public void processTransaction(Transaction transaction){
        long senderId = transaction.getSenderId();
        long recipientId = transaction.getRecipientId();
        float amount = transaction.getAmount();

        try {
            Optional<UserRecord> sender = userRepository.findById(senderId);
            Optional<UserRecord> recipient = userRepository.findById(recipientId);

            if (sender.isPresent() && recipient.isPresent()) {
                float balance = sender.get().getBalance();
                if (balance < amount) {
                    logger.warn("Sender doesn't have enough balance to make a transaction");
                } else {
                    // update balances
                    sender.get().setBalance(balance - amount);
                    float incentive = incentiveService.fetchIncentive(transaction);
                    recipient.get().setBalance(recipient.get().getBalance() + amount + incentive);

                    // save the updated user balance information
                    userRepository.save(sender.get());
                    userRepository.save(recipient.get());

                    // create and save transactions in a transaction table
                    TransactionRecord record = new TransactionRecord();
                    record.setSenderId(sender.get());
                    record.setRecipientId(sender.get());
                    record.setAmount(amount);
                    transactionRepository.save(record); // save the transaction to the database
                }
            } else {
                logger.error("Either sender or recipient does not exist, ignoring transaction");
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing the transaction: {}", e.toString());
        }
    }
}
