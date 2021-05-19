package com.sazonov.ioc.web.filters;

import com.sazonov.ioc.RequestMethod;
import com.sazonov.ioc.web.requestMapping.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RequestMapping(method = {RequestMethod.GET})
public @interface Filter {
    /**
     * Handle request path
     */
    String requestUriPath();

    int order();
}
