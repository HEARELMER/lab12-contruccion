package com.org.unsch.domain.ports.out;

import com.org.unsch.domain.model.Ticket;

import java.util.Optional;

public interface TicketRepositoryPort {

    Ticket save(Ticket ticket);

    Optional<Ticket> findByEventIdAndCustomerId(Long eventId, Long customerId);
}
