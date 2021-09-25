package com.example.demonaturalid;

import com.example.demonaturalid.entity.base.NaturalRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NaturalRepositoryImpl.class)
public class DemoNaturalIdApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoNaturalIdApplication.class, args);
	}

}
