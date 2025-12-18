package com.org.unsch.domain.model;

import java.time.Instant;
import java.util.Objects;

public class Ticket {

    private Long id;
    private Customer customer;
    private Event event;
    private TicketStatus status;
    private Instant paidAt;
    private Instant reservedAt;

    public Ticket() {
    }

    public Ticket(Long id, Customer customer, Event event, TicketStatus status, Instant paidAt, Instant reservedAt) {
        this.id = id;
        this.customer = customer;
        this.event = event;
        this.status = status;
        this.paidAt = paidAt;
        this.reservedAt = reservedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    public Instant getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(Instant reservedAt) {
        this.reservedAt = reservedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(customer, ticket.customer) && Objects.equals(event, ticket.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, event);
    }
}
