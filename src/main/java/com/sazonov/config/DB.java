package com.sazonov.config;

import java.util.Objects;

public final class DB {
    private static DB instance;

    private DB(){}

    public static DB getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DB();
        }
        return instance;
    }
}
