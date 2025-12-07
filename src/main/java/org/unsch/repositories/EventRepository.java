package org.unsch.repositories;

import org.springframework.data.repository.CrudRepository;
import org.unsch.models.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}