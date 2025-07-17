package com.ecommerce.customer.customer.CustomerMapper;

import com.ecommerce.customer.customer.Address;
import com.ecommerce.customer.customer.Customer;
import com.ecommerce.customer.customer.CustomerDTO.AddressDTO;
import com.ecommerce.customer.customer.CustomerDTO.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerDTO customerDto){
        Address address = Address.builder()
                .street(customerDto.address().street())
                .country(customerDto.address().country())
                .houseNo(customerDto.address().houseNo())
                .zipcode(customerDto.address().zipcode())
                .build();
        return Customer.builder()
                .name(customerDto.name())
                .email(customerDto.email())
                .address(address)
                .build();
    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        AddressDTO address = new AddressDTO(
                customer.getAddress().getId(),
                customer.getAddress().getStreet(),
                customer.getAddress().getHouseNo(),
                customer.getAddress().getCountry(),
                customer.getAddress().getZipcode()
                );
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                address
        );
    }
}
