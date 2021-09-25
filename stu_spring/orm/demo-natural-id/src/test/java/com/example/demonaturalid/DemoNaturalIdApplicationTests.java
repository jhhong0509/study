package com.example.demonaturalid;

import com.example.demonaturalid.entity.test.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DemoNaturalIdApplicationTests {

	@Autowired
	private TestRepository testRepository;

	@BeforeEach
	void setUp() {
		testRepository.save(new com.example.demonaturalid.entity.test.Test(null, "test1", "first"));
		testRepository.save(new com.example.demonaturalid.entity.test.Test(null, "test2", "second"));
		testRepository.save(new com.example.demonaturalid.entity.test.Test(null, "test3", "third"));
	}

	@Test
	void naturalId() {
		System.out.println("##########findByNaturalId");
		testRepository.findBySimpleNaturalId("test1");
		testRepository.findBySimpleNaturalId("test1");
		testRepository.findBySimpleNaturalId("test1");
		System.out.println("################end");
	}

//	@Test
//	void findById() {
//		System.out.println("##########findById");
//		testRepository.findById(1L);
//		testRepository.findById(1L);
//		testRepository.findById(1L);
//		testRepository.findById(1L);
//		System.out.println("###############end");
//	}

}
