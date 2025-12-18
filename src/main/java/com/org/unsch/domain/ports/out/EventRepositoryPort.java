package com.org.unsch.domain.ports.out;

import com.org.unsch.domain.model.Event;

import java.util.Optional;

public interface EventRepositoryPort {

    Event save(Event event);

    Optional<Event> findById(Long id);
}
