package com.example.demonaturalid.entity.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface NaturalRepository<T, ID extends Serializable, NID extends Serializable> extends JpaRepository<T, ID> {
    Optional<T> findBySimpleNaturalId(NID naturalId);
}
