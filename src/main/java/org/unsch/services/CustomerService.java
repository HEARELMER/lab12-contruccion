package org.unsch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unsch.models.Customer;
import org.unsch.repositories.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Transactional
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Customer> findByDni(String dni) {
        return repository.findByDni(dni);
    }

    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}