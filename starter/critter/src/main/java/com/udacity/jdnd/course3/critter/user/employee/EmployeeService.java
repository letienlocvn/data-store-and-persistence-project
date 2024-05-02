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

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

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

    public List<EmployeeDTO> findEmployeesWithRightSkillAreAvailable(EmployeeRequestDTO employeeDTO) {
        // Find employee available on that day
        List<Employee> employees = employeeRepository.findEmployeesByDaysAvailable(employeeDTO.getDate());
        List<Employee> employeesWithRightSkill = new ArrayList<>();
        List<EmployeeDTO> employeesWithRightSkillConvertDTO = new ArrayList<>();

        // Check database
        for (Employee employee : employees) {
            employee.setSkills(employeeDTO.getSkills());
            employeesWithRightSkill.add(employee);
        }

        employeesWithRightSkill.forEach(employee ->
                employeesWithRightSkillConvertDTO
                        .add(modelMapper.map(employee, EmployeeDTO.class)));


        return employeesWithRightSkillConvertDTO;
    }

}
