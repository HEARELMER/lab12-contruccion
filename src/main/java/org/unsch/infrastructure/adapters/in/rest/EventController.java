package org.unsch.infrastructure.adapters.in.rest;

import org.unsch.application.exception.BusinessValidationException;
import org.unsch.application.exception.DuplicateResourceException;
import org.unsch.application.exception.ResourceNotFoundException;
import org.unsch.domain.model.Event;
import org.unsch.domain.model.Partner;
import org.unsch.domain.ports.in.EventUseCase;
import org.unsch.infrastructure.adapters.in.rest.dto.EventDTO;
import org.unsch.infrastructure.adapters.in.rest.dto.PartnerDTO;
import org.unsch.infrastructure.adapters.in.rest.dto.SubscribeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "events")
public class EventController {

    private final EventUseCase eventUseCase;

    public EventController(EventUseCase eventUseCase) {
        this.eventUseCase = eventUseCase;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Event create(@RequestBody EventDTO dto) {
        Event event = new Event();
        event.setDate(LocalDate.parse(dto.getDate(), DateTimeFormatter.ISO_DATE));
        event.setName(dto.getName());
        event.setTotalSpots(dto.getTotalSpots());
        PartnerDTO partnerDTO = dto.getPartner();
        if (partnerDTO != null) {
            event.setPartner(new Partner(partnerDTO.getId(), null, null, null));
        }
        return eventUseCase.create(event);
    }

    @Transactional
    @PostMapping(value = "/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long id, @RequestBody SubscribeDTO dto) {
        try {
            Event event = eventUseCase.subscribe(id, dto.getCustomerId());
            return ResponseEntity.ok(new EventDTO(event));
        } catch (ResourceNotFoundException ex) {
            if ("Event not found".equals(ex.getMessage())) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        } catch (DuplicateResourceException | BusinessValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }
}
