package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;


public class OrderLineItemDTO {
    private String productId;
    private BigDecimal price;
    private int quantity;

    public OrderLineItemDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
