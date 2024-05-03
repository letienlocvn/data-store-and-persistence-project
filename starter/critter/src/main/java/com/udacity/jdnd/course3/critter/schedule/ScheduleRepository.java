package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> getSchedulesByEmployeesContains(Employee employee);
    List<Schedule> getSchedulesByPetsContains(Pet pet);
}
