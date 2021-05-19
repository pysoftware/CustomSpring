package com.sazonov.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class StringUtils {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
