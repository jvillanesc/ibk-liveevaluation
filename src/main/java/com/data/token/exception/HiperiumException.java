package com.data.token.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Getter
public class HiperiumException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

}
