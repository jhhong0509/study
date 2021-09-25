package com.example.demonaturalid.bootstrap;

import com.example.demonaturalid.entity.test.Test;
import com.example.demonaturalid.entity.test.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@Order(1000)
public class CommandLineRunnerTestSave implements CommandLineRunner {

    @Autowired
    private TestRepository testRepository;

    @Override
    public void run(String... args) throws Exception {
        testRepository.save(new Test(null, "test1", "content1"));
        testRepository.save(new Test(null, "test2", "content3"));
        testRepository.save(new Test(null, "test3", "content2"));
    }
}
