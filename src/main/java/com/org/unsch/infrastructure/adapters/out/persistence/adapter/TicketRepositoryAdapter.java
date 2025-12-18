package com.tu.proyecto.infrastructure.adapters.out.persistence.adapter;

import com.tu.proyecto.domain.model.Customer;
import com.tu.proyecto.domain.model.Event;
import com.tu.proyecto.domain.model.Ticket;
import com.tu.proyecto.domain.ports.out.TicketRepositoryPort;
import com.tu.proyecto.infrastructure.adapters.out.persistence.entity.CustomerEntity;
import com.tu.proyecto.infrastructure.adapters.out.persistence.entity.EventEntity;
import com.tu.proyecto.infrastructure.adapters.out.persistence.entity.TicketEntity;
import com.tu.proyecto.infrastructure.adapters.out.persistence.mapper.TicketEntityMapper;
import com.tu.proyecto.infrastructure.adapters.out.persistence.repository.TicketJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketRepositoryAdapter implements TicketRepositoryPort {

    private final TicketJpaRepository ticketJpaRepository;
    private final TicketEntityMapper ticketEntityMapper;

    public TicketRepositoryAdapter(TicketJpaRepository ticketJpaRepository, TicketEntityMapper ticketEntityMapper) {
        this.ticketJpaRepository = ticketJpaRepository;
        this.ticketEntityMapper = ticketEntityMapper;
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity entity = toEntity(ticket);
        TicketEntity saved = ticketJpaRepository.save(entity);
        return ticketEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Ticket> findByEventIdAndCustomerId(Long eventId, Long customerId) {
        return ticketJpaRepository.findByEventIdAndCustomerId(eventId, customerId)
                .map(ticketEntityMapper::toDomain);
    }

    private TicketEntity toEntity(Ticket ticket) {
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setStatus(ticket.getStatus());
        entity.setPaidAt(ticket.getPaidAt());
        entity.setReservedAt(ticket.getReservedAt());

        Customer customer = ticket.getCustomer();
        if (customer != null) {
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setId(customer.getId());
            customerEntity.setName(customer.getName());
            customerEntity.setDni(customer.getDni());
            customerEntity.setEmail(customer.getEmail());
            entity.setCustomer(customerEntity);
        }

        Event event = ticket.getEvent();
        if (event != null) {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setId(event.getId());
            entity.setEvent(eventEntity);
        }
        return entity;
    }
}