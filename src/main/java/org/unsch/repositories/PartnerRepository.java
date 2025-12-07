package org.unsch.repositories;

import org.springframework.data.repository.CrudRepository;
import org.unsch.models.Partner;

import java.util.Optional;

public interface PartnerRepository extends CrudRepository<Partner, Long> {

    Optional<Partner> findByRuc(String ruc);

    Optional<Partner> findByEmail(String email);
}