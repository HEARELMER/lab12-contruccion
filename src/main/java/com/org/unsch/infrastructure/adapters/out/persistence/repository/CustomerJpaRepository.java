package com.org.unsch.infrastructure.adapters.out.persistence.repository;

import com.org.unsch.infrastructure.adapters.out.persistence.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerJpaRepository extends CrudRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByDni(String dni);

    Optional<CustomerEntity> findByEmail(String email);
}
