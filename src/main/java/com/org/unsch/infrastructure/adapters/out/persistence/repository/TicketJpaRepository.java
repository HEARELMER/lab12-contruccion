package com.tu.proyecto.infrastructure.adapters.out.persistence.repository;

import com.tu.proyecto.infrastructure.adapters.out.persistence.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, Long> {

    Optional<TicketEntity> findByEventIdAndCustomerId(Long eventId, Long customerId);
}
