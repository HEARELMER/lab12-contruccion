package org.unsch.dtos;

import org.unsch.models.Customer;

public class CustomerDTO {
    private Long id;
    private String name;
    private String dni;
    private String email;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.dni = customer.getDni();
        this.email = customer.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}