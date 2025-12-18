package com.org.unsch.domain.ports.in;

import com.org.unsch.domain.model.Partner;

import java.util.Optional;

public interface PartnerUseCase {

    Partner create(Partner partner);

    Optional<Partner> findById(Long id);

    Optional<Partner> findByRuc(String ruc);

    Optional<Partner> findByEmail(String email);
}
