package com.org.unsch.infrastructure.adapters.in.rest;

import com.org.unsch.application.exception.DuplicateResourceException;
import com.org.unsch.domain.model.Customer;
import com.org.unsch.domain.ports.in.CustomerUseCase;
import com.org.unsch.infrastructure.adapters.in.rest.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "customers")
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    public CustomerController(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerDTO dto) {
        try {
            Customer customer = new Customer();
            customer.setName(dto.getName());
            customer.setDni(dto.getDni());
            customer.setEmail(dto.getEmail());

            Customer created = customerUseCase.create(customer);
            return ResponseEntity.created(URI.create("/customers/" + created.getId()))
                    .body(new CustomerDTO(created));
        } catch (DuplicateResourceException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return customerUseCase.findById(id)
                .<ResponseEntity<?>>map(customer -> ResponseEntity.ok(new CustomerDTO(customer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
