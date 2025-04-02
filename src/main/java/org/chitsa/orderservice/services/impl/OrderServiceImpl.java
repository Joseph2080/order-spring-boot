package org.chitsa.orderservice.services.impl;

import org.chitsa.orderservice.dto.OrderItemDto;
import org.chitsa.orderservice.dto.OrderRequestDto;
import org.chitsa.orderservice.dto.OrderResponseDto;
import org.chitsa.orderservice.exception.OrderNotFoundException;
import org.chitsa.orderservice.exception.UnauthorizedException;
import org.chitsa.orderservice.mapping.OrderDtoMapper;
import org.chitsa.orderservice.model.Order;
import org.chitsa.orderservice.repo.OrderRepo;
import org.chitsa.orderservice.services.OrderService;
import org.chitsa.orderservice.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ORDERS_NOT_FOUND_MESSAGE = "Orders not found";
    private static final String UNAUTHORIZED_DELETE_MESSAGE = "You are not authorized to delete this order";

    private final OrderRepo orderRepo;
    private final OrderDtoMapper orderDtoMapper;
    private final OrderValidator orderValidator;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo,
                            OrderDtoMapper orderDtoMapper, OrderValidator orderValidator) {
        this.orderRepo = orderRepo;
        this.orderDtoMapper = orderDtoMapper;
        this.orderValidator = orderValidator;
    }

    @Override
    public void createOrder(OrderRequestDto orderRequestDto, String customerId) {
        orderValidator.validateOrderRequest(orderRequestDto, customerId);
        orderRepo.save(orderDtoMapper.toOrder(orderRequestDto, customerId));
    }

    @Override
    public List<OrderResponseDto> findOrdersByCustomerId(String customerId) {
        return orderRepo.findOrdersByCustomerId(customerId)
                .orElseThrow(() -> new OrderNotFoundException(ORDERS_NOT_FOUND_MESSAGE))
                .stream()
                .map(orderDtoMapper::toOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(String orderId) {
        return findByOrderIdOrElseThrowException(orderId).getItems().stream()
                .map(orderDtoMapper::toOrderItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(String id) {
        orderRepo.delete(findByOrderIdOrElseThrowException(id));
    }

    @Override
    public void deleteOrder(String id, String customerId) {
        Order order = findByOrderIdOrElseThrowException(id);
        if (!order.getCustomerId().equals(customerId)) {
            throw new UnauthorizedException(UNAUTHORIZED_DELETE_MESSAGE);
        }
        orderRepo.delete(order);
    }

    @Override
    public OrderResponseDto findByOrderId(String orderId) {
        return orderDtoMapper.toOrderResponseDto(findByOrderIdOrElseThrowException(orderId));
    }

    public Order findByOrderIdOrElseThrowException(String orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}