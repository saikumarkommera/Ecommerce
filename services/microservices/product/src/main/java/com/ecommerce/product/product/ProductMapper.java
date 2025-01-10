package com.ecommerce.product.product;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toPrductDTO(Product product){
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory()
        );

    }
}
