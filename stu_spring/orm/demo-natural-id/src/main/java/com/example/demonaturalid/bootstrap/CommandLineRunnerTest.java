package com.example.demonaturalid.bootstrap;

import com.example.demonaturalid.entity.test.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@Order(10001)
public class CommandLineRunnerTest implements CommandLineRunner {

    @Autowired
    private TestRepository testRepository;

    @Override
    public void run(String... args) throws Exception {
        testRepository.findBySimpleNaturalId("test1");
        testRepository.findBySimpleNaturalId("test1");
        testRepository.findBySimpleNaturalId("test1");
    }
}
