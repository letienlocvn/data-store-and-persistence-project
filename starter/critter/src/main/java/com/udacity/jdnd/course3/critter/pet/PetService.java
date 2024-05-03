package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return mapToDTO(petRepository.save(pet));
    }

    private PetDTO mapToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet mapToEntity(PetDTO petDTO) {
        return modelMapper.map(petDTO, Pet.class);
    }

    public PetDTO findPet(long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "Id", petId));
        return modelMapper.map(pet, PetDTO.class);
    }

    public List<Pet> findAllById(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }
}
