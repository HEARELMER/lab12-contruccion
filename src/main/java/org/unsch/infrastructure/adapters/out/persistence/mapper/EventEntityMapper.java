package org.unsch.infrastructure.adapters.out.persistence.mapper;

import org.unsch.domain.model.Event;
import org.unsch.domain.model.Partner;
import org.unsch.domain.model.Ticket;
import org.unsch.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.unsch.infrastructure.adapters.out.persistence.entity.PartnerEntity;
import org.unsch.infrastructure.adapters.out.persistence.entity.TicketEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EventEntityMapper {

    private final PartnerEntityMapper partnerEntityMapper;
    private final TicketEntityMapper ticketEntityMapper;

    public EventEntityMapper(PartnerEntityMapper partnerEntityMapper, TicketEntityMapper ticketEntityMapper) {
        this.partnerEntityMapper = partnerEntityMapper;
        this.ticketEntityMapper = ticketEntityMapper;
    }

    public EventEntity toEntity(Event event) {
        if (event == null) {
            return null;
        }
        EventEntity entity = new EventEntity();
        entity.setId(event.getId());
        entity.setName(event.getName());
        entity.setDate(event.getDate());
        entity.setTotalSpots(event.getTotalSpots());
        PartnerEntity partnerEntity = partnerEntityMapper.toEntity(event.getPartner());
        entity.setPartner(partnerEntity);

        Set<TicketEntity> ticketEntities = new HashSet<>();
        for (Ticket ticket : event.getTickets()) {
            TicketEntity ticketEntity = ticketEntityMapper.toEntity(ticket, entity);
            ticketEntities.add(ticketEntity);
        }
        entity.setTickets(ticketEntities);
        return entity;
    }

    public Event toDomain(EventEntity entity) {
        if (entity == null) {
            return null;
        }
        Partner partner = partnerEntityMapper.toDomain(entity.getPartner());
        Event event = new Event(entity.getId(), entity.getName(), entity.getDate(), entity.getTotalSpots(), new HashSet<>(), partner);
        Set<Ticket> tickets = new HashSet<>();
        for (TicketEntity ticketEntity : entity.getTickets()) {
            Ticket ticket = ticketEntityMapper.toDomain(ticketEntity);
            ticket.setEvent(event);
            tickets.add(ticket);
        }
        event.setTickets(tickets);
        return event;
    }
}
