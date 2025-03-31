package org.chitsa.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.chitsa.orderservice.dto.OrderItemDto;
import org.chitsa.orderservice.dto.OrderRequestDto;
import org.chitsa.orderservice.dto.OrderResponseDto;
import org.chitsa.orderservice.exception.AuthenticationException;
import org.chitsa.orderservice.exception.ModelNotFoundException;
import org.chitsa.orderservice.exception.UnauthorizedException;
import org.chitsa.orderservice.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "API for managing orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new order", description = "Creates a new order in the system")
    public ResponseEntity<String> createNewOrder(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal Jwt jwt) {
        try {
            validateAuthHeader(jwt);
            orderService.createOrder(orderRequestDto, jwt.getSubject());
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order request: " + e.getMessage());
        }
    }

    @GetMapping("/customer-orders")
    @Operation(summary = "Get all orders for a customer", description = "Retrieves all orders for the authenticated customer")
    public ResponseEntity<List<OrderResponseDto>> getCustomerOrders(@AuthenticationPrincipal Jwt jwt) {
        validateAuthHeader(jwt);
        List<OrderResponseDto> orders = orderService.findOrdersByCustomerId(jwt.getSubject());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/details/{orderId}")
    @Operation(summary = "Get order details", description = "Retrieves an order based on its ID")
    public ResponseEntity<?> getOrderDetails(@PathVariable("orderId") String id) {
        try {
            List<OrderItemDto> orderItemDtoList = orderService.findOrderItemsByOrderId(id);
            return ResponseEntity.ok(orderItemDtoList);
        } catch (ModelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{orderId}")
    @Operation(summary = "Delete an order", description = "Deletes an order if the authenticated user is authorized")
    public ResponseEntity<String> removeOrder(@PathVariable("orderId") String id, @AuthenticationPrincipal Jwt jwt) {
        try {
            validateAuthHeader(jwt);
            String userId = jwt.getSubject();
            orderService.deleteOrder(id, userId);
            return ResponseEntity.ok("Order deleted successfully.");
        } catch (ModelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found: " + e.getMessage());
        }catch (UnauthorizedException unauthorizedException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(unauthorizedException.getMessage());
        }
    }

    private void validateAuthHeader(Jwt jwt){
        if(jwt == null || jwt.getSubject() == null){
            throw new AuthenticationException("Bearer token not provided");
        }
    }
}
