package com.sazonov.ioc.web.filters;

import com.sazonov.ioc.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Filter {
    /**
     * Handle request path
     */
    String requestUriPath();

    int order();
}
