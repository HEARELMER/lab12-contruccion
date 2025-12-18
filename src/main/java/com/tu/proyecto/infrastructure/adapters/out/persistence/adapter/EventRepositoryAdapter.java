package com.tu.proyecto.infrastructure.adapters.out.persistence.adapter;

import com.tu.proyecto.domain.model.Event;
import com.tu.proyecto.domain.ports.out.EventRepositoryPort;
import com.tu.proyecto.infrastructure.adapters.out.persistence.entity.EventEntity;
import com.tu.proyecto.infrastructure.adapters.out.persistence.mapper.EventEntityMapper;
import com.tu.proyecto.infrastructure.adapters.out.persistence.repository.EventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final EventJpaRepository eventJpaRepository;
    private final EventEntityMapper eventEntityMapper;

    public EventRepositoryAdapter(EventJpaRepository eventJpaRepository, EventEntityMapper eventEntityMapper) {
        this.eventJpaRepository = eventJpaRepository;
        this.eventEntityMapper = eventEntityMapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = eventEntityMapper.toEntity(event);
        EventEntity saved = eventJpaRepository.save(entity);
        return eventEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventJpaRepository.findById(id).map(eventEntityMapper::toDomain);
    }
}
