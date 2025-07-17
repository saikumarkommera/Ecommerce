package com.ecommerce.customer.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Address {
    @Id
    //@GeneratedValue :: Hibernate instructs the database to use its built-in mechanisms (like auto-increment or sequence) to generate the primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String street;
    private String houseNo;
    private String country;
    private int zipcode;
}
