package com.org.unsch.infrastructure.adapters.out.persistence.mapper;

import com.org.unsch.domain.model.Customer;
import com.org.unsch.domain.model.Ticket;
import com.org.unsch.infrastructure.adapters.out.persistence.entity.CustomerEntity;
import com.org.unsch.infrastructure.adapters.out.persistence.entity.EventEntity;
import com.org.unsch.infrastructure.adapters.out.persistence.entity.TicketEntity;
import org.springframework.stereotype.Component;

@Component
public class TicketEntityMapper {

    private final CustomerEntityMapper customerEntityMapper;

    public TicketEntityMapper(CustomerEntityMapper customerEntityMapper) {
        this.customerEntityMapper = customerEntityMapper;
    }

    public TicketEntity toEntity(Ticket ticket, EventEntity eventEntity) {
        if (ticket == null) {
            return null;
        }
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setStatus(ticket.getStatus());
        entity.setPaidAt(ticket.getPaidAt());
        entity.setReservedAt(ticket.getReservedAt());
        entity.setEvent(eventEntity);
        CustomerEntity customerEntity = customerEntityMapper.toEntity(ticket.getCustomer());
        entity.setCustomer(customerEntity);
        return entity;
    }

    public Ticket toDomain(TicketEntity entity) {
        if (entity == null) {
            return null;
        }
        Customer customer = customerEntityMapper.toDomain(entity.getCustomer());
        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());
        ticket.setCustomer(customer);
        ticket.setStatus(entity.getStatus());
        ticket.setPaidAt(entity.getPaidAt());
        ticket.setReservedAt(entity.getReservedAt());
        ticket.setEvent(null);
        return ticket;
    }
}
