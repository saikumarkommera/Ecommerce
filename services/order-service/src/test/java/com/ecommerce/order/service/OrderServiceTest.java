package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderLineItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderLineItem;
import com.ecommerce.order.exception.OrderException;
import com.ecommerce.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private String validUserId;
    private List<Order> mockOrders;

    @BeforeEach
    void setUp() {
        validUserId = "user123";
        
        // Create mock orders
        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        order1.setUserId(validUserId);
        order1.setOrderDate(LocalDate.now());
        order1.setTotalPrice(new BigDecimal("150.00"));
        
        OrderLineItem item1 = new OrderLineItem();
        item1.setProductId("prod1");
        item1.setPrice(new BigDecimal("75.00"));
        item1.setQuantity(2);
        item1.setOrder(order1);
        
        order1.setOrderLineItems(Arrays.asList(item1));
        
        Order order2 = new Order();
        order2.setId(UUID.randomUUID());
        order2.setUserId(validUserId);
        order2.setOrderDate(LocalDate.now().minusDays(1));
        order2.setTotalPrice(new BigDecimal("100.00"));
        
        OrderLineItem item2 = new OrderLineItem();
        item2.setProductId("prod2");
        item2.setPrice(new BigDecimal("100.00"));
        item2.setQuantity(1);
        item2.setOrder(order2);
        
        order2.setOrderLineItems(Arrays.asList(item2));
        
        mockOrders = Arrays.asList(order1, order2);
    }

    @Test
    void getOrdersByUserId_WithValidUserId_ReturnsOrderResponses() {
        // Arrange
        when(orderRepository.findByUserId(validUserId)).thenReturn(mockOrders);

        // Act
        List<OrderResponse> result = orderService.getOrdersByUserId(validUserId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verify first order
        OrderResponse firstOrder = result.get(0);
        assertEquals(mockOrders.get(0).getId(), firstOrder.getId());
        assertEquals(mockOrders.get(0).getOrderDate(), firstOrder.getOrderDate());
        assertEquals(mockOrders.get(0).getTotalPrice(), firstOrder.getTotalPrice());
        assertEquals(1, firstOrder.getOrderLineItems().size());
        
        // Verify first order line item
        OrderLineItemDTO firstItem = firstOrder.getOrderLineItems().get(0);
        assertEquals("prod1", firstItem.getProductId());
        assertEquals(new BigDecimal("75.00"), firstItem.getPrice());
        assertEquals(2, firstItem.getQuantity());
        
        verify(orderRepository, times(1)).findByUserId(validUserId);
    }

    @Test
    void getOrdersByUserId_WithNullUserId_ThrowsOrderException() {
        // Act & Assert
        OrderException exception = assertThrows(OrderException.class, 
            () -> orderService.getOrdersByUserId(null));
        
        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(orderRepository, never()).findByUserId(any());
    }

    @Test
    void getOrdersByUserId_WithEmptyUserId_ThrowsOrderException() {
        // Act & Assert
        OrderException exception = assertThrows(OrderException.class, 
            () -> orderService.getOrdersByUserId(""));
        
        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(orderRepository, never()).findByUserId(any());
    }

    @Test
    void getOrdersByUserId_WithWhitespaceUserId_ThrowsOrderException() {
        // Act & Assert
        OrderException exception = assertThrows(OrderException.class, 
            () -> orderService.getOrdersByUserId("   "));
        
        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(orderRepository, never()).findByUserId(any());
    }

    @Test
    void getOrdersByUserId_WithNoOrdersFound_ThrowsOrderException() {
        // Arrange
        when(orderRepository.findByUserId(validUserId)).thenReturn(Arrays.asList());

        // Act & Assert
        OrderException exception = assertThrows(OrderException.class, 
            () -> orderService.getOrdersByUserId(validUserId));
        
        assertEquals("No orders found for user ID: " + validUserId, exception.getMessage());
        verify(orderRepository, times(1)).findByUserId(validUserId);
    }

    @Test
    void getOrdersByUserId_WithEmptyOrderLineItems_ReturnsOrderWithEmptyItems() {
        // Arrange
        Order orderWithNoItems = new Order();
        orderWithNoItems.setId(UUID.randomUUID());
        orderWithNoItems.setUserId(validUserId);
        orderWithNoItems.setOrderDate(LocalDate.now());
        orderWithNoItems.setTotalPrice(new BigDecimal("0.00"));
        orderWithNoItems.setOrderLineItems(Arrays.asList());
        
        when(orderRepository.findByUserId(validUserId))
            .thenReturn(Arrays.asList(orderWithNoItems));

        // Act
        List<OrderResponse> result = orderService.getOrdersByUserId(validUserId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getOrderLineItems().isEmpty());
        
        verify(orderRepository, times(1)).findByUserId(validUserId);
    }
} 