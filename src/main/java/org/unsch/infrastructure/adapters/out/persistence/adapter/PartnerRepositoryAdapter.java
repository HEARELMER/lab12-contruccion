package org.unsch.infrastructure.adapters.out.persistence.adapter;

import org.unsch.domain.model.Partner;
import org.unsch.domain.ports.out.PartnerRepositoryPort;
import org.unsch.infrastructure.adapters.out.persistence.entity.PartnerEntity;
import org.unsch.infrastructure.adapters.out.persistence.mapper.PartnerEntityMapper;
import org.unsch.infrastructure.adapters.out.persistence.repository.PartnerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PartnerRepositoryAdapter implements PartnerRepositoryPort {

    private final PartnerJpaRepository partnerJpaRepository;
    private final PartnerEntityMapper partnerEntityMapper;

    public PartnerRepositoryAdapter(PartnerJpaRepository partnerJpaRepository, PartnerEntityMapper partnerEntityMapper) {
        this.partnerJpaRepository = partnerJpaRepository;
        this.partnerEntityMapper = partnerEntityMapper;
    }

    @Override
    public Partner save(Partner partner) {
        PartnerEntity entity = partnerEntityMapper.toEntity(partner);
        PartnerEntity saved = partnerJpaRepository.save(entity);
        return partnerEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Partner> findById(Long id) {
        return partnerJpaRepository.findById(id).map(partnerEntityMapper::toDomain);
    }

    @Override
    public Optional<Partner> findByRuc(String ruc) {
        return partnerJpaRepository.findByRuc(ruc).map(partnerEntityMapper::toDomain);
    }

    @Override
    public Optional<Partner> findByEmail(String email) {
        return partnerJpaRepository.findByEmail(email).map(partnerEntityMapper::toDomain);
    }
}
