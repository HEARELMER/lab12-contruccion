package org.unsch.infrastructure.adapters.out.persistence.adapter;

import org.unsch.domain.model.Customer;
import org.unsch.domain.ports.out.CustomerRepositoryPort;
import org.unsch.infrastructure.adapters.out.persistence.entity.CustomerEntity;
import org.unsch.infrastructure.adapters.out.persistence.mapper.CustomerEntityMapper;
import org.unsch.infrastructure.adapters.out.persistence.repository.CustomerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;

    public CustomerRepositoryAdapter(CustomerJpaRepository customerJpaRepository, CustomerEntityMapper customerEntityMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerEntityMapper = customerEntityMapper;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = customerEntityMapper.toEntity(customer);
        CustomerEntity saved = customerJpaRepository.save(entity);
        return customerEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id).map(customerEntityMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByDni(String dni) {
        return customerJpaRepository.findByDni(dni).map(customerEntityMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerJpaRepository.findByEmail(email).map(customerEntityMapper::toDomain);
    }
}
