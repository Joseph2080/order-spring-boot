package org.chitsa.orderservice.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequestDto {
    private List<OrderItemDto> items;
    public OrderRequestDto() {}

    public OrderRequestDto(String customerId, List<OrderItemDto> items, BigDecimal totalAmount) {
        this.items = items;
    }


    public List<OrderItemDto> getItems() {
        return items;
    }

}
