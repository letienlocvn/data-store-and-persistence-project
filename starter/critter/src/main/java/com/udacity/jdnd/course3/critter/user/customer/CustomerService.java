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
        List<Pet> pets = new ArrayList<>();
        if (customerDTO.getPetIds() != null
                && !customerDTO.getPetIds().isEmpty()) {
            pets = customerDTO.getPetIds()
                    .stream()
                    .map(petId -> petRepository.getOne(petId))
                    .collect(Collectors.toList());
        }
        Customer customer = mapToEntity(customerDTO);
        customer.setPets(pets);
        customerRepository.save(customer);
        return mapToDTO(customer);
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
        Customer customer = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId)).getCustomer();
        // Customer customer = petRepository.getOne(petId).getCustomer();
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
