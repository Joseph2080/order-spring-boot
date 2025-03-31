package org.chitsa.orderservice.mapping;

import org.chitsa.orderservice.dto.OrderItemDto;
import org.chitsa.orderservice.dto.OrderRequestDto;
import org.chitsa.orderservice.dto.OrderResponseDto;
import org.chitsa.orderservice.model.Order;
import org.chitsa.orderservice.model.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoMapper {

    public OrderResponseDto toOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getCustomerId(),
                calculateTotalAmount(order.getItems()),
                order.getCreatedAt().toString()
        );
    }

    public Order toOrder(OrderRequestDto orderRequestDto, String customerId) {
        return new Order(
                customerId,
                orderRequestDto.getItems().stream()
                        .map(this::toOrderItem)
                        .collect(Collectors.toList())
        );
    }

    public OrderItem toOrderItem(OrderItemDto orderItemDto) {
        return new OrderItem(
                orderItemDto.getProductName(),
                orderItemDto.getQuantity(),
                orderItemDto.getPrice()
        );
    }

    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProductName(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(item -> BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}