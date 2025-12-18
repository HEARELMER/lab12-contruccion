package com.tu.proyecto.application.service;

import com.tu.proyecto.application.exception.DuplicateResourceException;
import com.tu.proyecto.domain.model.Partner;
import com.tu.proyecto.domain.ports.in.PartnerUseCase;
import com.tu.proyecto.domain.ports.out.PartnerRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PartnerService implements PartnerUseCase {

    private final PartnerRepositoryPort partnerRepositoryPort;

    public PartnerService(PartnerRepositoryPort partnerRepositoryPort) {
        this.partnerRepositoryPort = partnerRepositoryPort;
    }

    @Override
    @Transactional
    public Partner create(Partner partner) {
        if (partnerRepositoryPort.findByRuc(partner.getRuc()).isPresent()) {
            throw new DuplicateResourceException("Partner already exists");
        }
        if (partnerRepositoryPort.findByEmail(partner.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Partner already exists");
        }
        return partnerRepositoryPort.save(partner);
    }

    @Override
    public Optional<Partner> findById(Long id) {
        return partnerRepositoryPort.findById(id);
    }

    @Override
    public Optional<Partner> findByRuc(String ruc) {
        return partnerRepositoryPort.findByRuc(ruc);
    }

    @Override
    public Optional<Partner> findByEmail(String email) {
        return partnerRepositoryPort.findByEmail(email);
    }
}
