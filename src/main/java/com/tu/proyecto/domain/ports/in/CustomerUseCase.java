package com.tu.proyecto.domain.ports.in;

import com.tu.proyecto.domain.model.Customer;

import java.util.Optional;

public interface CustomerUseCase {

    Customer create(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByDni(String dni);

    Optional<Customer> findByEmail(String email);
}
