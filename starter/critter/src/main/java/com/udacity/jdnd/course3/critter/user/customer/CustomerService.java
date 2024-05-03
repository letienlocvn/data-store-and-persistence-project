package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // Mapping to entity
        Customer entityCustomer = modelMapper.map(customerDTO, Customer.class);

        return modelMapper.map(customerRepository.save(entityCustomer), CustomerDTO.class);

        // Customer customer = customerRepository.findById(customerDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerDTO.getId()));
//        if (customer != null) {
//            // Update later
//            // customer.setName(customerDTO.getName());
//            return modelMapper.map(customerRepository.save(customer), CustomerDTO.class);
//        } else {
//            // Save new customer
//        }
    }

    public List<CustomerDTO> findAllCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach(customer -> {
            CustomerDTO customerDTO = mapToDTO(customer);
            customerDTOS.add(customerDTO);
        });
        return customerDTOS;
    }

    public CustomerDTO findCustomerByPet(long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        Customer customer = customerRepository.getCustomerByPetsContains(pet);

        return mapToDTO(customer);
    }

    public Customer mapToEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPetIds(customer.getPets()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList()));
        return customerDTO;
    }


}
