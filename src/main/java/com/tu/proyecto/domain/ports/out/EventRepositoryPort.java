package com.tu.proyecto.domain.ports.out;

import com.tu.proyecto.domain.model.Event;

import java.util.Optional;

public interface EventRepositoryPort {

    Event save(Event event);

    Optional<Event> findById(Long id);
}
