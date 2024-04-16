package com.data.token.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BussinesErrorEnum {

    TOKEN_INVALID("ERROR-BS-001", "Token invalido");

    private final String code;
    private final String message;

}
