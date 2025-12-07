package org.unsch.repositories;

import org.springframework.data.repository.CrudRepository;
import org.unsch.models.Ticket;

import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByEventIdAndCustomerId(Long id, Long customerId);
}