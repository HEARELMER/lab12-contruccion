package org.unsch.controllers;

import org.unsch.dtos.CustomerDTO;
import org.unsch.models.Customer;
import org.unsch.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerDTO dto) {
        if (customerService.findByDni(dto.getDni()).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Customer already exists");
        }
        if (customerService.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Customer already exists");
        }

        var customer = new Customer();
        customer.setName(dto.getName());
        customer.setDni(dto.getDni());
        customer.setEmail(dto.getEmail());

        customer = customerService.save(customer);

        return ResponseEntity.created(URI.create("/customers/" + customer.getId())).body(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var customer = customerService.findById(id);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer.get());
    }
}
