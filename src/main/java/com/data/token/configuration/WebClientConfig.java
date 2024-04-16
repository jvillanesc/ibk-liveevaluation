package com.data.token.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("tokenWebClient")
    public WebClient tokenWebClient(@Value("${token-client.urls.base-url}") String serviceUrl) {
        return WebClient.builder()
                .baseUrl(serviceUrl)
                .build();
    }


}
