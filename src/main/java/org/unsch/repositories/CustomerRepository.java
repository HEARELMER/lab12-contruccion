package org.unsch.repositories;

import org.springframework.data.repository.CrudRepository;
import org.unsch.models.Customer;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByDni(String dni);

    Optional<Customer> findByEmail(String email);
}