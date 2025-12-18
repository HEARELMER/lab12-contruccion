package org.unsch.infrastructure.adapters.in.rest.dto;

import org.unsch.domain.model.Partner;

public class PartnerDTO {
    private Long id;
    private String name;
    private String ruc;
    private String email;

    public PartnerDTO() {
    }

    public PartnerDTO(Long id) {
        this.id = id;
    }

    public PartnerDTO(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.ruc = partner.getRuc();
        this.email = partner.getEmail();
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
