package org.unsch.application.service;

import org.unsch.application.exception.DuplicateResourceException;
import org.unsch.domain.model.Customer;
import org.unsch.domain.ports.in.CustomerUseCase;
import org.unsch.domain.ports.out.CustomerRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService implements CustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        if (customerRepositoryPort.findByDni(customer.getDni()).isPresent()) {
            throw new DuplicateResourceException("Customer already exists");
        }
        if (customerRepositoryPort.findByEmail(customer.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Customer already exists");
        }
        return customerRepositoryPort.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepositoryPort.findById(id);
    }

    @Override
    public Optional<Customer> findByDni(String dni) {
        return customerRepositoryPort.findByDni(dni);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepositoryPort.findByEmail(email);
    }
}
