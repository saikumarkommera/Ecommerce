package com.ecommerce.product.service;

import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.product.Product;
import com.ecommerce.product.product.ProductDTO;
import com.ecommerce.product.product.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final RedisService redis;

    public List<ProductDTO> getAllProducts() {
        Object obj = this.redis.get("products");
        if(obj!=null){
            log.info("Getting products from cache");
            return (List<ProductDTO>)obj;
        }
        List<ProductDTO> products =  this.repository.findAll().stream()
                .map(mapper::toProductDTO)
                .collect(Collectors.toList());
        this.redis.set("products",products,300l);
        return products;
    }

    public ProductDTO getProductById(String prodId) {
        Object obj = this.redis.get(prodId);
        if(obj!=null) {
            log.info("Getting product from cache");
            return (ProductDTO)obj;
        }
        ProductDTO product = this.repository.findById(prodId).map(mapper::toProductDTO).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        this.redis.set(prodId,product,300l);
        return product;
    }
}
