package com.sazonov.ioc.web.requestMapping;


import com.sazonov.ioc.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    RequestMethod[] method() default {};
}
