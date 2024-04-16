package com.data.token.service;

import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<String> token();

}
