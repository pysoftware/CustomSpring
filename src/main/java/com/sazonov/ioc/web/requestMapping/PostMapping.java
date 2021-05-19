package com.sazonov.ioc.web.requestMapping;

import com.sazonov.ioc.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@RequestMapping(method = {RequestMethod.POST})
public @interface PostMapping {
    String name() default "";
}
