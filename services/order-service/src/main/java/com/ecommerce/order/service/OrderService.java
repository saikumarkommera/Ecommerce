package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderLineItemDTO;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderLineItem;
import com.ecommerce.order.exception.OrderException;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    public String create(OrderRequest request) {
        List<OrderLineItem> orderLineItems = request.getItems().stream()
                .map(orderItem -> {
                    OrderLineItem orderLineItem = new OrderLineItem();
                    orderLineItem.setProductId(orderItem.getProductId());
                    orderLineItem.setPrice(orderItem.getPrice());
                    orderLineItem.setQuantity(orderItem.getQuantity());
                    return orderLineItem;
                }).toList();
        
        System.out.println("OrderItems : " + orderLineItems);
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setOrderDate(LocalDate.now());
        order.setOrderLineItems(orderLineItems);
        BigDecimal totalPrice = orderLineItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        orderLineItems.forEach(item -> item.setOrder(order));
        orderRepo.save(order);
        return "Order Placed Successfully";
    }

    public List<OrderResponse> getOrdersByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new OrderException("User ID cannot be null or empty");
        }
        List<Order> orders = orderRepo.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new OrderException("No orders found for user ID: " + userId);
        }
        return orders.stream()
                .map(this::mapToOrderResponse)
                .toList();
    }
    
    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setTotalPrice(order.getTotalPrice());
        List<OrderLineItemDTO> orderLineItemDTOs = order.getOrderLineItems().stream()
                .map(this::mapToOrderLineItemDTO)
                .toList();
        response.setOrderLineItems(orderLineItemDTOs);
        return response;
    }
    
    private OrderLineItemDTO mapToOrderLineItemDTO(OrderLineItem lineItem) {
        OrderLineItemDTO dto = new OrderLineItemDTO();
        dto.setPrice(lineItem.getPrice());
        dto.setQuantity(lineItem.getQuantity());
        dto.setProductId(lineItem.getProductId());
        return dto;
    }
}
