package com.data.token.service;

import com.data.token.model.domain.ClientCredential;
import com.data.token.model.domain.Token;

import com.data.token.service.impl.AccountServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AccoutServiceTest {

    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    private WebClient.RequestHeadersSpec requestHeadersMock;
    private WebClient.RequestBodySpec requestBodyMock;
    private WebClient.ResponseSpec responseMock;
    private WebClient webClientMock;

    @BeforeEach
    void mockWebClient() {
        requestBodyUriMock = mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersMock = mock(WebClient.RequestHeadersSpec.class);
        requestBodyMock = mock(WebClient.RequestBodySpec.class);
        responseMock = mock(WebClient.ResponseSpec.class);
        webClientMock = mock(WebClient.class);
    }

    @Mock
    private WebClient tokenWebClient;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("generacionTokenExitoso")
    void generacionTokenExitoso() {

        //Act
        String expectedUri = "http://localhost/Accounts/{AccountSid}/Messages.json";

        when(tokenWebClient.post())
                .thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(eq(expectedUri), eq("accountSid"))).thenReturn(requestBodyMock);
        when(requestBodyMock.bodyValue(eq(getClientCredential()))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Token.class)).thenReturn(Mono.just(getToken()));
        String expectedResponse = "abcd";

        //Arrange
        Mono<String> tokenResult = accountService.token();

        //Assert
        StepVerifier.create(tokenResult)
                    .assertNext(token -> Assertions.assertThat(token).isEqualTo(expectedResponse))
                    .verifyComplete();
    }

    private Token getToken() {
        return Token.builder()
                .accessToken("abcd")
                .tokenType("Bearer")
                .expireAt(LocalDateTime.now())
                .build();
    }

    private ClientCredential getClientCredential() {
        return ClientCredential.builder()
                .client_id("")
                .client_secret("")
                .audience("")
                .grant_type("")
                .build();
    }


}
