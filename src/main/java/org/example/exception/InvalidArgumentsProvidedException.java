package org.example.exception;

import java.io.IOException;

public class InvalidArgumentsProvidedException extends IOException {
    public InvalidArgumentsProvidedException(String message) {
        super(message);
    }
}
