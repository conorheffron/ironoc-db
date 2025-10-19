package com.ironoc.db.enums;

import lombok.Getter;

@Getter
public enum DataSourceKey {

    DRIVER_CLASS_NAME("spring.datasource.driver-class-name"),
    DS_URL("spring.datasource.url"),
    DS_USERNAME("spring.datasource.username"),
    GCP_SEC_VER("google.cloud.db.secret.version");

    private final String key;

    DataSourceKey(String key) {
        this.key = key;
    }
}
