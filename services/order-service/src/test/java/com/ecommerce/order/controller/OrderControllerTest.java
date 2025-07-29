package com.ecommerce.order.controller;

import com.ecommerce.order.dto.OrderLineItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private String validUserId;
    private List<OrderResponse> mockOrderResponses;

    @BeforeEach
    void setUp() {
        validUserId = "user123";
        
        // Create mock order responses
        OrderResponse order1 = new OrderResponse();
        order1.setId(UUID.randomUUID());
        order1.setOrderDate(LocalDate.now());
        order1.setTotalPrice(new BigDecimal("150.00"));
        
        OrderLineItemDTO item1 = new OrderLineItemDTO();
        item1.setProductId("prod1");
        item1.setPrice(new BigDecimal("75.00"));
        item1.setQuantity(2);
        
        order1.setOrderLineItems(Arrays.asList(item1));
        
        OrderResponse order2 = new OrderResponse();
        order2.setId(UUID.randomUUID());
        order2.setOrderDate(LocalDate.now().minusDays(1));
        order2.setTotalPrice(new BigDecimal("100.00"));
        
        OrderLineItemDTO item2 = new OrderLineItemDTO();
        item2.setProductId("prod2");
        item2.setPrice(new BigDecimal("100.00"));
        item2.setQuantity(1);
        
        order2.setOrderLineItems(Arrays.asList(item2));
        
        mockOrderResponses = Arrays.asList(order1, order2);
    }

    @Test
    void getOrdersByUserId_WithValidUserId_ReturnsOrders() {
        // Arrange
        when(orderService.getOrdersByUserId(validUserId)).thenReturn(mockOrderResponses);

        // Act
        ResponseEntity<List<OrderResponse>> response = orderController.getOrdersByUserId(validUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(mockOrderResponses, response.getBody());
        
        verify(orderService, times(1)).getOrdersByUserId(validUserId);
    }

    @Test
    void getOrdersByUserId_WithNullUserId_ReturnsBadRequest() {
        // Act
        ResponseEntity<List<OrderResponse>> response = orderController.getOrdersByUserId(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(orderService, never()).getOrdersByUserId(any());
    }

    @Test
    void getOrdersByUserId_WithEmptyUserId_ReturnsBadRequest() {
        // Act
        ResponseEntity<List<OrderResponse>> response = orderController.getOrdersByUserId("");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(orderService, never()).getOrdersByUserId(any());
    }

    @Test
    void getOrdersByUserId_WithWhitespaceUserId_ReturnsBadRequest() {
        // Act
        ResponseEntity<List<OrderResponse>> response = orderController.getOrdersByUserId("   ");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(orderService, never()).getOrdersByUserId(any());
    }

    @Test
    void getOrdersByUserId_WithEmptyOrderList_ReturnsEmptyList() {
        // Arrange
        when(orderService.getOrdersByUserId(validUserId)).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<OrderResponse>> response = orderController.getOrdersByUserId(validUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        
        verify(orderService, times(1)).getOrdersByUserId(validUserId);
    }
} 