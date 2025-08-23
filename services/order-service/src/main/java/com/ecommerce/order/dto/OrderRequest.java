package com.ecommerce.order.dto;

import java.util.List;

public class OrderRequest {
    private String userId;
    private List<OrderItem> items;

    public OrderRequest(String userId, List<OrderItem> items) {
        this.userId = userId;
        this.items = items;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
