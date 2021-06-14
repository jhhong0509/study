package com.webflux.auth.global.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
@Profile("{!local, !test}")
public class MongoDBConfiguration extends AbstractReactiveMongoConfiguration {
    @Value("${db_url}")
    private String dbUrl;

    @Value("${db_name}")
    private String dbName;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(dbUrl);
    }
}
