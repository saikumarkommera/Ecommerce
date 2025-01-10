package com.ecommerce.customer.customer.CustomerDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record AddressDTO(
        Integer id,
        String street,
        String houseNo,
        String country,
        int zipcode
) {
}
