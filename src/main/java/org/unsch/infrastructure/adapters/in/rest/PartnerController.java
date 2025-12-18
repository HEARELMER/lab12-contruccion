package org.unsch.infrastructure.adapters.in.rest;

import org.unsch.application.exception.DuplicateResourceException;
import org.unsch.domain.model.Partner;
import org.unsch.domain.ports.in.PartnerUseCase;
import org.unsch.infrastructure.adapters.in.rest.dto.PartnerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "partners")
public class PartnerController {

    private final PartnerUseCase partnerUseCase;

    public PartnerController(PartnerUseCase partnerUseCase) {
        this.partnerUseCase = partnerUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PartnerDTO dto) {
        try {
            Partner partner = new Partner();
            partner.setName(dto.getName());
            partner.setRuc(dto.getRuc());
            partner.setEmail(dto.getEmail());

            Partner created = partnerUseCase.create(partner);

            return ResponseEntity.created(URI.create("/partners/" + created.getId()))
                    .body(new PartnerDTO(created));
        } catch (DuplicateResourceException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return partnerUseCase.findById(id)
                .<ResponseEntity<?>>map(partner -> ResponseEntity.ok(new PartnerDTO(partner)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
