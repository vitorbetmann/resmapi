package com.vitorbetmann.resmapi.exceptions;

public class FieldAlreadyInUseException extends RuntimeException {

    public FieldAlreadyInUseException(String message) {
        super(message);
    }
}
