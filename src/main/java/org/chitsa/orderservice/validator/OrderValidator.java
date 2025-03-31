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
        validateNotNull(orderRequestDto, ERROR_ORDER_REQUEST_NULL);
        validateNotEmpty(customerId, ERROR_CUSTOMER_ID_EMPTY);
        validateNotEmpty(orderRequestDto.getItems(), ERROR_ORDER_ITEMS_EMPTY);
        validateOrderItems(orderRequestDto.getItems());
    }

    private void validateOrderItems(List<OrderItemDto> orderItemList) {
        for (OrderItemDto item : orderItemList) {
            validateNotEmpty(item.getProductName(), ERROR_PRODUCT_NAME_EMPTY);
            validatePositive(item.getQuantity(), ERROR_QUANTITY_NOT_POSITIVE);
            validatePositive(item.getPrice(), ERROR_PRICE_NOT_POSITIVE);
        }
    }

    private void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNotEmpty(List<?> list, String message) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validatePositive(Double value, String message) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validatePositive(Integer value, String message) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
}