package com.artvantage.exceptions;

import java.util.function.Supplier;

public class ContentNotFoundException extends Exception {

    public ContentNotFoundException(String message) {
        super(message);
    }

    public ContentNotFoundException orElseThrow(Supplier<Throwable> throwableSupplier) throws Throwable {
        return this;
    }
}

