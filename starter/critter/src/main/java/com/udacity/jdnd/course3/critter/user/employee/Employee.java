package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {

    @ElementCollection
    @Enumerated
    private Set<EmployeeSkill> skills;

    // @ManyToMany(mappedBy = "employees")
    @ElementCollection
    @Enumerated
    private Set<DayOfWeek> daysAvailable;

}
