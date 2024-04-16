package com.data.token.service.impl;

import com.data.token.model.domain.ClientCredential;
import com.data.token.model.domain.Token;
import com.data.token.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    @Qualifier("tokenWebClient")
    private WebClient tokenWebClient;

    @Value("${token-client.urls.post-token-url}")
    private String postTokenUrl;

    @Value("${credential-client.client-id}")
    private String clientId;

    @Value("${credential-client.client-secret}")
    private String clientSecret;

    @Value("${credential-client.audience}")
    private String audience;

    @Value("${credential-client.grant-type}")
    private String grantType;

    @Override
    public Mono<String> token() {
        return tokenWebClient.post()
                .uri(postTokenUrl)
                .body(BodyInserters.fromObject(ClientCredential.builder()
                                .client_id(clientId)
                                .client_secret(clientSecret)
                                .audience(audience)
                                .grant_type(grantType)
                                .build()))
                //.exchange()
                .retrieve()
                .bodyToMono(Token.class)
                //.flatMap(resposne -> resposne.body(BodyExtractors.toMono(Token.class)))
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException) {
                        var responseException = (WebClientResponseException) e;
                        if (responseException.getStatusCode().is4xxClientError()) {
                            return Mono.error(new ResponseStatusException(responseException.getStatusCode()));
                        }
                        if (responseException.getStatusCode().is5xxServerError()) {
                            return Mono.error(new ResponseStatusException(responseException.getStatusCode()));
                        }
                    }

                    return Mono.error(e);
                })
                .map(token -> token.getAccessToken());
    }
}
