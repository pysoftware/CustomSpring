package com.sazonov.ioc.web.response;

public class ResponseEntity<T> {

    private final T responseBody;

    public ResponseEntity(T responseBody) {
        this.responseBody = responseBody;
    }

    private static ResponseEntity ok() {
        return null;
    }
}
