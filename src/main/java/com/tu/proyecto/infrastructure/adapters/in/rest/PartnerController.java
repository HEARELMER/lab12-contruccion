package com.tu.proyecto.infrastructure.adapters.in.rest;

import com.tu.proyecto.application.exception.DuplicateResourceException;
import com.tu.proyecto.domain.model.Partner;
import com.tu.proyecto.domain.ports.in.PartnerUseCase;
import com.tu.proyecto.infrastructure.adapters.in.rest.dto.PartnerDTO;
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
