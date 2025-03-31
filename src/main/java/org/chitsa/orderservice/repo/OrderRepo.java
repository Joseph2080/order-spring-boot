package org.chitsa.orderservice.repo;

import org.chitsa.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends MongoRepository<Order, String> {
    Optional<List<Order>> findOrdersByCustomerId(String customerId);
}
