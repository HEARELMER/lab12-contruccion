package com.tu.proyecto.domain.ports.out;

import com.tu.proyecto.domain.model.Ticket;

import java.util.Optional;

public interface TicketRepositoryPort {

    Ticket save(Ticket ticket);

    Optional<Ticket> findByEventIdAndCustomerId(Long eventId, Long customerId);
}
