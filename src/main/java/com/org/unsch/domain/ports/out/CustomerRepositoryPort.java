package com.tu.proyecto.domain.ports.out;

import com.tu.proyecto.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepositoryPort {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByDni(String dni);

    Optional<Customer> findByEmail(String email);
}
