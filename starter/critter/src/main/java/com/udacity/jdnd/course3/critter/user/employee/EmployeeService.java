package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        Employee entityEmployee = mapToEntity(employeeDTO);
        return mapToDTO(employeeRepository.save(entityEmployee));
    }

    public void updateActivities(long employeeId, Set<DayOfWeek> daysAvailable) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<EmployeeDTO> findEmployeesWithRightSkillAreAvailable(EmployeeRequestDTO employeeDTO) {
        // Find employee available on that day with right skill
        List<Employee> employees = employeeRepository
                .findEmployeesByDaysAvailable(employeeDTO.getDate().getDayOfWeek())
                .stream()
                .filter(employee -> employee.getSkills()
                        .containsAll(employeeDTO.getSkills()))
                .collect(Collectors.toList());

        List<EmployeeDTO> employeesWithRightSkillConvertDTO = new ArrayList<>();

        employees.forEach(employee -> employeesWithRightSkillConvertDTO
                .add(modelMapper.map(employee, EmployeeDTO.class)));


        return employeesWithRightSkillConvertDTO;
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    private Employee mapToEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

    public EmployeeDTO findEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public List<Employee> findAllById(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }
}
