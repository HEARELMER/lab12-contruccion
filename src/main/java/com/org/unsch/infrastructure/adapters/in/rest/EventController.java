package com.tu.proyecto.infrastructure.adapters.in.rest;

import com.tu.proyecto.application.exception.BusinessValidationException;
import com.tu.proyecto.application.exception.DuplicateResourceException;
import com.tu.proyecto.application.exception.ResourceNotFoundException;
import com.tu.proyecto.domain.model.Event;
import com.tu.proyecto.domain.model.Partner;
import com.tu.proyecto.domain.ports.in.EventUseCase;
import com.tu.proyecto.infrastructure.adapters.in.rest.dto.EventDTO;
import com.tu.proyecto.infrastructure.adapters.in.rest.dto.PartnerDTO;
import com.tu.proyecto.infrastructure.adapters.in.rest.dto.SubscribeDTO;
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
