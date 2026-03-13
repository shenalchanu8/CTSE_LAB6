package com.example.inventoryservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListener {

    @KafkaListener(topics = "order-topic", groupId = "inventory-service")
    public void onOrderCreated(String message) {
        System.out.println("[Inventory] Received order event: " + message);
        // TODO: update stock logic
    }
}
