package org.chitsa.orderservice.model;


import java.util.UUID;

public class OrderItem {
    private final String productId;
    private String productName;
    private Integer quantity;
    private Double price;

    public OrderItem() {
        productId = UUID.randomUUID().toString();
    }

    public OrderItem(String productName, Integer quantity, Double price) {
        productId = UUID.randomUUID().toString();
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {return productId;}

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
