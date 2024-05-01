package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // Mapping to entity
        Customer entityCustomer = modelMapper.map(customerDTO, Customer.class);

        Customer customer = customerRepository.findById(customerDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerDTO.getId()));
        if (customer != null) {
            // Update later
            // customer.setName(customerDTO.getName());
            return modelMapper.map(customerRepository.save(customer), CustomerDTO.class);
        } else {
            // Save new customer
            return modelMapper.map(customerRepository.save(entityCustomer), CustomerDTO.class);
        }
    }
}
