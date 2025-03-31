package org.chitsa.orderservice.dto;

import java.math.BigDecimal;

public class OrderResponseDto {
    private String orderId;
    private String customerId;
    private BigDecimal orderTotal;
    private String createdAt;

    public OrderResponseDto(String orderId, String customerId, BigDecimal orderTotal, String createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderTotal = orderTotal;
        this.createdAt = createdAt;
    }

    public OrderResponseDto() {}

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "OrderResponseDto [orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderTotal=" + orderTotal +
                ", createdAt=" + createdAt + "]";
    }
}
