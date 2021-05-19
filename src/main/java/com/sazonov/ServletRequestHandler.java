package com.sazonov;

import com.sazonov.ioc.AppContextInitializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

@WebServlet(urlPatterns = "/*")
@Slf4j
public class ServletRequestHandler extends CustomHttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        DriverManager.getConnection("localhost:3309", "root", "root");
        log.info("test {} {}", req.getPathInfo(), resp);
        AppContextInitializer.getInstance().handleHttpRequest(req, resp);
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Hello1!");
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}