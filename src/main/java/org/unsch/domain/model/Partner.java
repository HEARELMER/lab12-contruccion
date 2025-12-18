package org.unsch.domain.model;

import java.util.Objects;

public class Partner {

    private Long id;
    private String name;
    private String ruc;
    private String email;

    public Partner() {
    }

    public Partner(Long id, String name, String ruc, String email) {
        this.id = id;
        this.name = name;
        this.ruc = ruc;
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partner partner = (Partner) o;
        return Objects.equals(id, partner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
