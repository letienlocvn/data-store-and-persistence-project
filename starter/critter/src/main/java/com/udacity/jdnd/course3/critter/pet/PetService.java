package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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

        Customer customer = customerRepository.findById(petDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", petDTO.getOwnerId()));
        Pet pet = mapToEntity(petDTO);

        pet.setCustomer(customer);

        return convertPetToPetDTO(petRepository.save(pet));
    }

    // Other solution: Using BeanUtils to copy
    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    // This solution: Not work - Because pet cannot set OwnerId of customer
    private PetDTO mapToDTO(Pet pet) {
        return modelMapper.map(pet, PetDTO.class);
    }

    private Pet mapToEntity(PetDTO petDTO) {
        return modelMapper.map(petDTO, Pet.class);
    }
}
