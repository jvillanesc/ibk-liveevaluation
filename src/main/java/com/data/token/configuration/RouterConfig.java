package com.data.token.configuration;

import com.data.token.handlers.AccountHanler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> clientRouters(AccountHanler accountHanler) {
        return RouterFunctions.route()
                .path("/api", builder -> builder
                        .POST("/tokens", accept(MediaType.APPLICATION_JSON), accountHanler::token))
                .build();
    }


    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }



}
