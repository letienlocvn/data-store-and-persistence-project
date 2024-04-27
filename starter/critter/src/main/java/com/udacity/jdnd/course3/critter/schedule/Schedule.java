package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "schedule")
    private List<Employee> employees;

    @ManyToMany(mappedBy = "schedule")
    private List<Pet> pets;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_skill")
    private EmployeeSkill activities;
}
