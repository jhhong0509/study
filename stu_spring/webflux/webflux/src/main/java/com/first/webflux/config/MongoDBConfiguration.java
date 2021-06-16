package com.first.webflux.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
@Profile("!local")
public class MongoDBConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${db_url}")
    private String dbUrl;

    @Value("${db_name}")
    private String dbName;

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(dbUrl);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}