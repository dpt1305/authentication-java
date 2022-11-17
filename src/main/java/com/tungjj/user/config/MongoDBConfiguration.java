package com.tungjj.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@EnableConfigurationProperties
public class MongoDBConfiguration {

    @Bean("authenticationMongoDB")
    @ConfigurationProperties(prefix = "spring.data.mongodb")
    @Value("${uri=@null}")
    public MongoProperties getAuthentication() {
        return new MongoProperties();
    }

    @Bean("mongo_template")
    public MongoTemplate getAuthenticationMongoTemplate() {
        return new MongoTemplate(authenticationMongoFactory(getAuthentication()));
    }

    @Bean
    public MongoDatabaseFactory authenticationMongoFactory(MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }
}
