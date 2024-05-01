package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_skill")
    private EmployeeSkill skills;

    @ManyToMany(mappedBy = "employees")
    private Set<Schedule> schedule = new HashSet<>();

}
