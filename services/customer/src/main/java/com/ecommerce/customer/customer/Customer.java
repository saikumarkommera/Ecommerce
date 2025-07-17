package com.ecommerce.customer.customer;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    //cascade = CascadeType.ALL :: If a User is persisted, the Address is also persisted automatically.
    //If a User is deleted, the Address is also deleted.
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
