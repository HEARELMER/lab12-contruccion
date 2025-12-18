package com.org.unsch.infrastructure.adapters.out.persistence.repository;

import com.org.unsch.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventJpaRepository extends CrudRepository<EventEntity, Long> {
}
