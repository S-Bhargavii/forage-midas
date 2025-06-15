package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // this is the transaction id

    @ManyToOne
    private UserRecord senderId;

    @ManyToOne
    private UserRecord recipientId;

    private float amount;

    public Long getId() {
        return id;
    }

    public UserRecord getSenderId() {
        return senderId;
    }

    public float getAmount() {
        return amount;
    }

    public UserRecord getRecipientId() {
        return recipientId;
    }

    public void setSenderId(UserRecord senderId) {
        this.senderId = senderId;
    }

    public void setRecipientId(UserRecord recipientId) {
        this.recipientId = recipientId;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}