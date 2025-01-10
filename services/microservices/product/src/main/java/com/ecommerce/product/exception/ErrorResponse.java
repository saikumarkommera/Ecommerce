package com.ecommerce.product.exception;

import java.util.Map;

public record ErrorResponse(Map<String,String> error) {
}
