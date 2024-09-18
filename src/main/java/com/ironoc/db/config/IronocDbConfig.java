package com.ironoc.db.config;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.ironoc.db.enums.DataSourceKey;
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

    @Bean
    @Profile("h2")
    public DataSource dataSource(@Autowired Environment environment) {
        DataSourceBuilder dsBuilder = DataSourceBuilder.create();
        dsBuilder.driverClassName(environment.getRequiredProperty(DataSourceKey.DRIVER_CLASS_NAME.getKey()));
        dsBuilder.url(environment.getRequiredProperty(DataSourceKey.DS_URL.getKey()));
        dsBuilder.username(environment.getRequiredProperty(DataSourceKey.DS_USERNAME.getKey()));
        dsBuilder.password(getDataSourceSecret(environment.getRequiredProperty(DataSourceKey.GCP_SEC_VER.getKey())));
        dsBuilder.type(HikariDataSource.class);
        return dsBuilder.build();
    }

    private String getDataSourceSecret(String secretVersion) {
        String secretValue = "";
        try {
            SecretManagerServiceClient client = SecretManagerServiceClient.create();
            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersion);
            secretValue = response.getPayload().getData().toStringUtf8();
        } catch (Exception e) {
            log.error("Unexpected error occurred extracting GCP secret.", e);
        }
        return secretValue;
    }
}