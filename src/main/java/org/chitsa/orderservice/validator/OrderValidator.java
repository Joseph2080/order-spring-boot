package org.chitsa.orderservice.validator;

import org.chitsa.orderservice.dto.OrderItemDto;
import org.chitsa.orderservice.dto.OrderRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderValidator {
    // Error message constants
    private static final String ERROR_ORDER_REQUEST_NULL = "Order request cannot be null.";
    private static final String ERROR_CUSTOMER_ID_EMPTY = "Customer ID cannot be null or empty.";
    private static final String ERROR_ORDER_ITEMS_EMPTY = "Order must contain at least one item.";
    private static final String ERROR_PRODUCT_NAME_EMPTY = "Product name cannot be null or empty.";
    private static final String ERROR_QUANTITY_NOT_POSITIVE = "Quantity must be greater than 0.";
    private static final String ERROR_PRICE_NOT_POSITIVE = "Price must be greater than 0.";

    public void validateOrderRequest(OrderRequestDto orderRequestDto, String customerId) {
        validateNotNull(orderRequestDto);
        validateNotEmpty(customerId, ERROR_CUSTOMER_ID_EMPTY);
        validateNotEmpty(orderRequestDto.getItems());
        validateOrderItems(orderRequestDto.getItems());
    }

    private void validateOrderItems(List<OrderItemDto> orderItemList) {
        for (OrderItemDto item : orderItemList) {
            validateNotEmpty(item.getProductName(), ERROR_PRODUCT_NAME_EMPTY);
            validatePositive(item.getQuantity());
            validatePositive(item.getPrice());
        }
    }

    private void validateNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException(OrderValidator.ERROR_ORDER_REQUEST_NULL);
        }
    }

    private void validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNotEmpty(List<?> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(OrderValidator.ERROR_ORDER_ITEMS_EMPTY);
        }
    }

    private void validatePositive(Double value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(OrderValidator.ERROR_PRICE_NOT_POSITIVE);
        }
    }

    private void validatePositive(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(OrderValidator.ERROR_QUANTITY_NOT_POSITIVE);
        }
    }
}