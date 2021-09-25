package com.example.demonaturalid.entity.test;

import com.example.demonaturalid.entity.base.NaturalRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends NaturalRepository<Test, Long, String> {
}
