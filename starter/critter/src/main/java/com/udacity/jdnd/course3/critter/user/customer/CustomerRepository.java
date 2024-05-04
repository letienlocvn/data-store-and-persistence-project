package com.udacity.jdnd.course3.critter.user.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    // Customer getCustomerByPetsContains(Pet pet);
}
