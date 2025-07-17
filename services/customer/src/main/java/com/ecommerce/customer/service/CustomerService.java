package com.ecommerce.customer.service;

import com.ecommerce.customer.customer.CustomerDTO.CustomerDTO;
import com.ecommerce.customer.customer.CustomerMapper.CustomerMapper;
import com.ecommerce.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper mapper;

    public Integer createCus(CustomerDTO customerDTO) {
        var customer = this.customerRepo.save(this.mapper.toCustomer(customerDTO));
        return customer.getId();
    }

    public boolean isCustomExists(Integer id) {
        return this.customerRepo.findById(id).isPresent();
    }

    public CustomerDTO getCustomer(Integer id) throws Exception {
        return this.customerRepo.findById(id).map(mapper::toCustomerDTO).orElseThrow(() -> new CustomerNotFoundException("Customer not found with the id"));
    }
}
