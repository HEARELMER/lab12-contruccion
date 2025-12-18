package org.unsch.infrastructure.adapters.out.persistence.adapter;

import org.unsch.domain.model.Event;
import org.unsch.domain.ports.out.EventRepositoryPort;
import org.unsch.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.unsch.infrastructure.adapters.out.persistence.mapper.EventEntityMapper;
import org.unsch.infrastructure.adapters.out.persistence.repository.EventJpaRepository;
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
