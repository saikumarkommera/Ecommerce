package com.ecommerce.customer.customer.CustomerDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CustomerDTO(
        Integer id,
        @NotNull(message = "Customer name should not be null")
        @NotEmpty(message = "Customer name should not be empty")
        String name,
        @Email(message = "Email is not valid")
        String email,
        AddressDTO address
        ) {

}
