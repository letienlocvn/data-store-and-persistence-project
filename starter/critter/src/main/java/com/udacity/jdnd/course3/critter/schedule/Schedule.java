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

    @ManyToMany
    @JoinTable(name = "employee_schedule",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(name = "pet_schedule",
            joinColumns = {@JoinColumn(name = "pet_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    private List<Pet> pets;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_skill")
    private EmployeeSkill activities;
}
