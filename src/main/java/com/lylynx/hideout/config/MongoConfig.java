package com.lylynx.hideout.config;

import com.lylynx.hideout.db.mongo.converters.DateTimeToDateConverter;
import com.lylynx.hideout.db.mongo.converters.DateToDateTimeConverter;
import com.lylynx.hideout.db.mongo.converters.StringToGrantedAuthorityConverter;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableMongoRepositories("com.lylynx.hideout")
class MongoConfig extends AbstractMongoConfiguration {

    @Value("${mongo.host}")
    private String mongoHost;
    @Value("${mongo.port}")
    private String mongoPort;

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("/persistence.properties"));
        return ppc;
    }

    @Override
    protected String getDatabaseName() {
        return Constants.MONGO_DATABASE;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new Mongo(mongoHost, Integer.parseInt(mongoPort));
    }

    @Override
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new DateTimeToDateConverter());
        converterList.add(new DateToDateTimeConverter());
        converterList.add(new StringToGrantedAuthorityConverter());
        return new CustomConversions(converterList);
    }
}
