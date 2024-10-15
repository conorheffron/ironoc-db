package com.ironoc.db.service;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoogleCloudClientImpl implements GoogleCloudClient {

    @Override
    public String getSecret(String secretVersion) {
        log.info("Entering GoogleCloudClient.getSecret for secretVersion={}", secretVersion);
        String secretValue = "";
        try {
            SecretManagerServiceClient client = SecretManagerServiceClient.create();
            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersion);
            secretValue = response.getPayload().getData().toStringUtf8();
            log.info("Extracting GCP secret key={}", secretVersion);
        } catch (Exception e) {
            log.error("Unexpected error occurred extracting GCP secret.", e);
        }
        return secretValue;
    }
}
