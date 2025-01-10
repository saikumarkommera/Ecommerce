package com.ecommerce.customer.controller;

import com.ecommerce.customer.customer.CustomerDTO.CustomerDTO;
import com.ecommerce.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Integer> createCustomer(@RequestBody @Valid CustomerDTO customer){
        return ResponseEntity.ok(customerService.createCus(customer));
    }

    @GetMapping("/isExist/{id}")
    public ResponseEntity<Boolean> isCustomerExits(@PathVariable("id") Integer id){
        return ResponseEntity.ok(customerService.isCustomExists(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(customerService.getCustomer(id));

    }
}
