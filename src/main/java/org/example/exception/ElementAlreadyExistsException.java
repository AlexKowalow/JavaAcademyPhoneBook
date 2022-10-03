package org.example.exception;

import java.io.IOException;

public class ElementAlreadyExistsException extends IOException {
    public ElementAlreadyExistsException(String message) {
        super(message);
    }
}
