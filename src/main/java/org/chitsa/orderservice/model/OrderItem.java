package org.chitsa.orderservice.model;


public class OrderItem {
    private String productName;
    private Integer quantity;
    private Double price;

    public OrderItem() {

    }

    public OrderItem(String productName, Integer quantity, Double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

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
        return "OrderItem [productName=" + productName + ", quantity=" + quantity + "]";
    }
}
