package com.tu.proyecto.infrastructure.adapters.out.persistence.repository;

import com.tu.proyecto.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventJpaRepository extends CrudRepository<EventEntity, Long> {
}
