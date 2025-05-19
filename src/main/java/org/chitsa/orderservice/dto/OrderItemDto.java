package org.chitsa.orderservice.dto;

public class OrderItemDto {
    private String productName;
    private Integer quantity;
    private Double price;

    public OrderItemDto() {
    }

    public OrderItemDto(String productName, Integer quantity, Double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}