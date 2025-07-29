package com.ecommerce.order.repository;

import com.ecommerce.order.entity.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, UUID> {
}
