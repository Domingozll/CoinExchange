package com.bizzan.bc.wallet.config;

import com.bizzan.bc.wallet.converter.BigDecimalToDecimal128Converter;
import com.bizzan.bc.wallet.converter.Decimal128ToBigDecimalConverter;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty(name = "spring.data.mongodb.uri")
public class MongodbConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String uri;


    public MongoClient getMongoClientURI() {
        return MongoClients.create(new ConnectionString(uri));
    }

    @Override
    protected String getDatabaseName() {
        return new ConnectionString(uri).getDatabase();
    }

    @Bean
    @Override
    public MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(new ConnectionString(uri));
    }

    @Bean
    public MongoMappingContext mongoMappingContext() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        return mappingContext;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory dbFactory, MongoMappingContext context) {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(dbFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
        List<Object> list = new ArrayList<>();
        list.add(new BigDecimalToDecimal128Converter());//自定义的类型转换器
        list.add(new Decimal128ToBigDecimalConverter());//自定义的类型转换器
        converter.setCustomConversions(new CustomConversions(list));
        return converter;
    }


    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory dbFactory, MappingMongoConverter converter) {
        return new MongoTemplate(dbFactory, converter);
    }
}
