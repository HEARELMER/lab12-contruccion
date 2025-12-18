package org.unsch.domain.ports.out;

import org.unsch.domain.model.Ticket;

import java.util.Optional;

public interface TicketRepositoryPort {

    Ticket save(Ticket ticket);

    Optional<Ticket> findByEventIdAndCustomerId(Long eventId, Long customerId);
}
