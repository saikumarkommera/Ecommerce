package com.ecommerce.product.product;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record ProductDTO (
     String id,
     String name,
     String description,
     Integer availableQuantity,
     BigDecimal price,
     Category category
){}
