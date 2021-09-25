package com.example.demonaturalid.entity.base;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

@Transactional(readOnly = true)
public class NaturalRepositoryImpl<T, ID extends Serializable, NID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements NaturalRepository<T, ID, NID> {
    private final EntityManager entityManager;
    public NaturalRepositoryImpl(JpaEntityInformation entityInformation,
                                 EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }
    @Override
    public Optional<T> findBySimpleNaturalId(NID naturalId) {
        Optional<T> entity = entityManager.unwrap(Session.class)
                .bySimpleNaturalId(getDomainClass())
                .loadOptional(naturalId);
        return entity;
    }
}
