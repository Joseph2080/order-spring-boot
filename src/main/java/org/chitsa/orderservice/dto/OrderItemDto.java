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

    public double getSubtotal() {
        return price * quantity;
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
        return "OrderItemDTO{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}