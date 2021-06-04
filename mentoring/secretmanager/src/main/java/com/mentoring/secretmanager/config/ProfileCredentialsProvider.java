package com.mentoring.secretmanager.config;

import com.amazonaws.auth.*;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor
public class ProfileCredentialsProvider extends AWSCredentialsProviderChain {
    public ProfileCredentialsProvider(@Nullable final String profile) {
        super(new DefaultAWSCredentialsProviderChain(), new EC2ContainerCredentialsProviderWrapper(),
                new EnvironmentVariableCredentialsProvider(), new SystemPropertiesCredentialsProvider(),
                profile.isBlank() ? new ProfileCredentialsProvider()
                        : new ProfileCredentialsProvider(profile));
        this.setReuseLastProvider(true);
    }
}
