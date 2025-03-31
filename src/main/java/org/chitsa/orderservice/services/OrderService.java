package org.chitsa.orderservice.services;

import org.chitsa.orderservice.dto.OrderItemDto;
import org.chitsa.orderservice.dto.OrderRequestDto;
import org.chitsa.orderservice.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    void createOrder(OrderRequestDto  orderRequestDto, String customerId);
    List<OrderResponseDto> findOrdersByCustomerId(String customerId);
    void deleteOrder(String id);
    void deleteOrder(String id, String customerId);
    OrderResponseDto findByOrderId(String id);
    List<OrderItemDto> findOrderItemsByOrderId(String orderId);

}
