package com.sazonov;

import com.sazonov.ioc.AppContextInitializer;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Slf4j
public class ServletContextInitializer implements ServletContextListener {
    public static void main(String[] args) {
        AppContextInitializer.getInstance().init("com.sazonov");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("contextInitialized");
        AppContextInitializer.getInstance().init(this.getClass().getPackage().getName());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("contextDestroyed");
    }
}
