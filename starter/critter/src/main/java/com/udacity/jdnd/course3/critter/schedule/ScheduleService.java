package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.User;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;


    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        List<Employee> employees = employeeService.findAllById(scheduleDTO.getEmployeeIds());
        List<Pet> pets = petService.findAllById(scheduleDTO.getPetIds());
        Schedule schedule = mapToEntity(scheduleDTO, employees, pets);
        scheduleRepository.save(schedule);

        return mapToDTO(schedule);
    }

    public List<ScheduleDTO> findScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        List<Schedule> schedules = scheduleRepository.getSchedulesByEmployeesContains(employee);

        return schedules.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> findScheduleForPet(long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        List<Schedule> schedules = scheduleRepository.getSchedulesByPetsContains(pet);

        return schedules.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> findScheduleForCustomer(long customerId) {
        // Find all pet has customer id.
        List<Pet> pets = petRepository.getPetsByCustomer_Id(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        pets.forEach(pet -> {
            Pet petEntity = petRepository.findById(pet.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", pet.getId()));
            List<Schedule> schedules = scheduleRepository.getSchedulesByPetsContains(petEntity);
            schedules.forEach(schedule -> {
                ScheduleDTO scheduleDTO = mapToDTO(schedule);
                scheduleDTOS.add(scheduleDTO);
            });
        });
        return scheduleDTOS;
    }

    private Schedule mapToEntity(ScheduleDTO scheduleDTO,
                                 List<Employee> employees,
                                 List<Pet> pets) {
        Schedule schedule = new Schedule();
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        return schedule;
    }

    private ScheduleDTO mapToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees()
                .stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets()
                .stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());

        return scheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }


}
