package org.chitsa.orderservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String customerId;
    private List<OrderItem> items;
    private LocalDateTime createdAt;

    public Order() {
        createdAt = LocalDateTime.now();
    }

    public Order(String customerId, List<OrderItem> items) {
        this.customerId = customerId;
        this.items = items;
        createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
