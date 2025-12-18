package com.org.unsch.infrastructure.adapters.out.persistence.mapper;

import com.org.unsch.domain.model.Customer;
import com.org.unsch.infrastructure.adapters.out.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {

    public CustomerEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setDni(customer.getDni());
        entity.setEmail(customer.getEmail());
        return entity;
    }

    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Customer(entity.getId(), entity.getName(), entity.getDni(), entity.getEmail());
    }
}
