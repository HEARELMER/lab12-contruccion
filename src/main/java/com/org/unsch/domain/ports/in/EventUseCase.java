package com.org.unsch.domain.ports.in;

import com.org.unsch.domain.model.Event;
import com.org.unsch.domain.model.Ticket;

import java.util.Optional;

public interface EventUseCase {

    Event create(Event event);

    Optional<Event> findById(Long id);

    Event subscribe(Long eventId, Long customerId);

    Optional<Ticket> findTicketByEventIdAndCustomerId(Long eventId, Long customerId);
}
