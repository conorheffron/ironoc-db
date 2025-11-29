package com.ironoc.db.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironoc.db.enums.DataSourceKey;
import com.ironoc.db.service.GoogleCloudClient;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = { "com.ironoc.db" })
@Slf4j
public class IronocDbConfig {

    private final Environment environment;

    private final GoogleCloudClient googleCloudClient;

    @Autowired
    public IronocDbConfig(Environment environment, GoogleCloudClient googleCloudClient) {
        this.environment = environment;
        this.googleCloudClient = googleCloudClient;
    }

    @Bean
    @Profile("h2")
    public DataSource dataSource() {
        DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
        dsBuilder.driverClassName(environment.getRequiredProperty(DataSourceKey.DRIVER_CLASS_NAME.getKey()));
        dsBuilder.url(environment.getRequiredProperty(DataSourceKey.DS_URL.getKey()));
        dsBuilder.username(environment.getRequiredProperty(DataSourceKey.DS_USERNAME.getKey()));
        dsBuilder.password(googleCloudClient.getSecret(environment.getRequiredProperty(
                DataSourceKey.GCP_SEC_VER.getKey())));
        dsBuilder.type(HikariDataSource.class);
        return dsBuilder.build();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
