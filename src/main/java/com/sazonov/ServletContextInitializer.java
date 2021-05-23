package com.sazonov;

import com.sazonov.controllers.RestController;
import com.sazonov.ioc.AppContextInitializer;
import com.sazonov.ioc.Component;
import com.sazonov.ioc.Controller;
import com.sazonov.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import javax.naming.ldap.Control;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
@Slf4j
public class ServletContextInitializer implements ServletContextListener {
    public static void main(String[] args) {
        log.info("{}", ObjectUtils.objectHasAnnotation(RestController.class, Component.class));
//        List<Object> objects = new ArrayList<>();
//        objects.add(new Object());
//        objects.add(new Object());
//        for (Object object : objects) {
//            log.info("{}", object);
//        }
//        AppContextInitializer.INSTANCE.init("com.sazonov");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("contextInitialized {}", this.getClass().getPackage().getName());
        AppContextInitializer.INSTANCE.init(this.getClass().getPackage().getName());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("contextDestroyed");
    }
}
