package com.data.token.exception;

public class BussinesException extends HiperiumException {
    public BussinesException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
