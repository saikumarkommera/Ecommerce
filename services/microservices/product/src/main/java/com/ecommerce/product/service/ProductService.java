package com.ecommerce.product.service;

import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.product.ProductDTO;
import com.ecommerce.product.product.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductDTO> getAllProducts() {
        return this.repository.findAll().stream()
                .map(mapper::toPrductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(String prodId) {
        return this.repository.findById(prodId).map(mapper::toPrductDTO).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
