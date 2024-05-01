package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PetDTO createNewPet(PetDTO petDTO) {
        // Checking if pet name is exits.
        Pet entityPet = modelMapper.map(petDTO, Pet.class);
        Pet pet = petRepository.findById(petDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petDTO.getId()));
        if (!pet.getName().equals(entityPet.getName())) {
            // Checking if customer exits
            Customer customer = customerRepository.findById(petDTO.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", petDTO.getOwnerId()));
            if (customer != null) {
                return modelMapper.map(petRepository.save(entityPet), PetDTO.class);
            }
        }
        return null;
    }
}
