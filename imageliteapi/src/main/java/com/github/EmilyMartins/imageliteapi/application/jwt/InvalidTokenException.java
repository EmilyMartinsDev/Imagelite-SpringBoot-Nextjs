package com.github.EmilyMartins.imageliteapi.application.jwt;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}