package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public PetDTO createNewPet(PetDTO petDTO) {

        Customer customer = customerRepository.findById(petDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", petDTO.getOwnerId()));
        Pet pet = mapToEntity(petDTO, customer);
        Pet petEntity = petRepository.save(pet);
        customerRepository.save(customer);
        return mapToDTO(petEntity);
    }

    public PetDTO findPet(long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "Id", petId));

        return mapToDTO(pet);
    }

    public List<Pet> findAllById(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }

    public List<PetDTO> findAllPets() {
        List<PetDTO> petDTOS = new ArrayList<>();
        Iterable<Pet> pets = petRepository.findAll();
        pets.forEach(pet -> {
            PetDTO petDTO = mapToDTO(pet);
            petDTOS.add(petDTO);
        });
        return petDTOS;
    }

    public List<PetDTO> findPetsByOwner(long ownerId) {
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> pets = petRepository.getPetsByCustomer_Id(ownerId);
        pets.forEach(pet -> {
            PetDTO petDTO = mapToDTO(pet);
            petDTOS.add(petDTO);
        });
        return petDTOS;
    }

    private PetDTO mapToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet mapToEntity(PetDTO petDTO, Customer customer) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setNotes(petDTO.getNotes());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setCustomer(customer);
        customer.savePet(pet);
        return pet;
    }


}
