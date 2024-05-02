package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        Employee entityEmployee = modelMapper.map(employeeDTO, Employee.class);
        return modelMapper.map(employeeRepository.save(entityEmployee), EmployeeDTO.class);
    }

    public void updateActivities(long employeeId, Set<DayOfWeek> daysAvailable) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employee.setDaysAvailable(daysAvailable);
    }
}
