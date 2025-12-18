package com.org.unsch.application.service;

import com.org.unsch.application.exception.BusinessValidationException;
import com.org.unsch.application.exception.DuplicateResourceException;
import com.org.unsch.application.exception.ResourceNotFoundException;
import com.org.unsch.domain.model.Customer;
import com.org.unsch.domain.model.Event;
import com.org.unsch.domain.model.Partner;
import com.org.unsch.domain.model.Ticket;
import com.org.unsch.domain.model.TicketStatus;
import com.org.unsch.domain.ports.in.EventUseCase;
import com.org.unsch.domain.ports.out.CustomerRepositoryPort;
import com.org.unsch.domain.ports.out.EventRepositoryPort;
import com.org.unsch.domain.ports.out.PartnerRepositoryPort;
import com.org.unsch.domain.ports.out.TicketRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class EventService implements EventUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;
    private final PartnerRepositoryPort partnerRepositoryPort;
    private final EventRepositoryPort eventRepositoryPort;
    private final TicketRepositoryPort ticketRepositoryPort;

    public EventService(CustomerRepositoryPort customerRepositoryPort,
                        PartnerRepositoryPort partnerRepositoryPort,
                        EventRepositoryPort eventRepositoryPort,
                        TicketRepositoryPort ticketRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
        this.partnerRepositoryPort = partnerRepositoryPort;
        this.eventRepositoryPort = eventRepositoryPort;
        this.ticketRepositoryPort = ticketRepositoryPort;
    }

    @Override
    @Transactional
    public Event create(Event event) {
        if (event.getPartner() == null || event.getPartner().getId() == null) {
            throw new ResourceNotFoundException("Partner not found");
        }
        Partner partner = partnerRepositoryPort.findById(event.getPartner().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));
        event.setPartner(partner);
        return eventRepositoryPort.save(event);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepositoryPort.findById(id);
    }

    @Override
    @Transactional
    public Event subscribe(Long eventId, Long customerId) {
        Customer customer = customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Event event = eventRepositoryPort.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        ticketRepositoryPort.findByEventIdAndCustomerId(eventId, customerId)
                .ifPresent(ticket -> {
                    throw new DuplicateResourceException("Email already registered");
                });

        if (event.getTotalSpots() < event.getTickets().size() + 1) {
            throw new BusinessValidationException("Event sold out");
        }

        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setEvent(event);
        ticket.setReservedAt(Instant.now());
        ticket.setStatus(TicketStatus.PENDING);

        Ticket savedTicket = ticketRepositoryPort.save(ticket);
        event.getTickets().add(savedTicket);
        eventRepositoryPort.save(event);

        return event;
    }

    @Override
    public Optional<Ticket> findTicketByEventIdAndCustomerId(Long eventId, Long customerId) {
        return ticketRepositoryPort.findByEventIdAndCustomerId(eventId, customerId);
    }
}
