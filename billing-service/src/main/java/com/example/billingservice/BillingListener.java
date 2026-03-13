package com.example.billingservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BillingListener {

    @KafkaListener(topics = "order-topic", groupId = "billing-service")
    public void onOrderCreated(String message) {
        System.out.println("[Billing] Received order event: " + message);
        // TODO: billing/invoice logic
    }
}
