package com.audicin.backend.track.api.exceptions.controller;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
