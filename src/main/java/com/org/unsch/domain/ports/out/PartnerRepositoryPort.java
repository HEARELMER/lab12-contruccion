package com.org.unsch.domain.ports.out;

import com.org.unsch.domain.model.Partner;

import java.util.Optional;

public interface PartnerRepositoryPort {

    Partner save(Partner partner);

    Optional<Partner> findById(Long id);

    Optional<Partner> findByRuc(String ruc);

    Optional<Partner> findByEmail(String email);
}
