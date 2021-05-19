package com.sazonov;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Arrays;

@Slf4j
public class CustomHttpServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("TESTIK");
        Arrays.stream(this.getClass().getFields()).forEach(item -> log.info(item.getName()));
    }
}
