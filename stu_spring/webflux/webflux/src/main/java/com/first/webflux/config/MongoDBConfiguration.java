package com.first.webflux.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfiguration {

    @Value("${db_url}")
    private String dbUrl;

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(dbUrl);
    }
}