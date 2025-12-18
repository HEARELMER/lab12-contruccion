package org.unsch.infrastructure.adapters.out.persistence.repository;

import org.unsch.infrastructure.adapters.out.persistence.entity.PartnerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PartnerJpaRepository extends CrudRepository<PartnerEntity, Long> {

    Optional<PartnerEntity> findByRuc(String ruc);

    Optional<PartnerEntity> findByEmail(String email);
}
