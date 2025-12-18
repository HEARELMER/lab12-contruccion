package com.tu.proyecto.infrastructure.adapters.out.persistence.repository;

import com.tu.proyecto.infrastructure.adapters.out.persistence.entity.PartnerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PartnerJpaRepository extends CrudRepository<PartnerEntity, Long> {

    Optional<PartnerEntity> findByRuc(String ruc);

    Optional<PartnerEntity> findByEmail(String email);
}
