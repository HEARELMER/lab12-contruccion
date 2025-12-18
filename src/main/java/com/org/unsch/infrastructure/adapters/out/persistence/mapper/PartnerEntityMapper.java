package com.org.unsch.infrastructure.adapters.out.persistence.mapper;

import com.org.unsch.domain.model.Partner;
import com.org.unsch.infrastructure.adapters.out.persistence.entity.PartnerEntity;
import org.springframework.stereotype.Component;

@Component
public class PartnerEntityMapper {

    public PartnerEntity toEntity(Partner partner) {
        if (partner == null) {
            return null;
        }
        PartnerEntity entity = new PartnerEntity();
        entity.setId(partner.getId());
        entity.setName(partner.getName());
        entity.setRuc(partner.getRuc());
        entity.setEmail(partner.getEmail());
        return entity;
    }

    public Partner toDomain(PartnerEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Partner(entity.getId(), entity.getName(), entity.getRuc(), entity.getEmail());
    }
}
