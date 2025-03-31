package org.chitsa.orderservice.config;

import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicAwsAuthConfig {
    private final String accessKey;
    private final String secretKey;

    public BasicAwsAuthConfig(@Value("${aws.credentials.access-key}") String accessKey,
                              @Value("${aws.credentials.secret-key}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }
}
