package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PetType type;

    @Nationalized
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private LocalDate birthDate;
    private String notes;

}
