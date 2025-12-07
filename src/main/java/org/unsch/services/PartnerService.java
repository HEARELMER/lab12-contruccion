package org.unsch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unsch.models.Partner;
import org.unsch.repositories.PartnerRepository;

import java.util.Optional;

@Service
public class PartnerService {

    @Autowired
    private PartnerRepository repository;

    @Transactional
    public Partner save(Partner customer) {
        return repository.save(customer);
    }

    public Optional<Partner> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Partner> findByRuc(String ruc) {
        return repository.findByRuc(ruc);
    }

    public Optional<Partner> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}