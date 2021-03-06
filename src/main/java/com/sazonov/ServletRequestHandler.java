package com.sazonov;

import com.sazonov.filters.AuthFilter;
import com.sazonov.ioc.AppContextInitializer;
import com.sazonov.repositories.CitiesRepositoryImpl;
import com.sazonov.responses.CitiesResponse;
import com.sazonov.utils.JsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/*")
@Slf4j
public class ServletRequestHandler extends CustomHttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppContextInitializer.INSTANCE.handleHttpRequest(req, resp);
        AuthFilter authFilter = new AuthFilter();
        authFilter.filter(req, resp);
        resp.setContentType("application/json");
        CitiesRepositoryImpl citiesRepository = new CitiesRepositoryImpl();
        String json = JsonUtils.objectToJson(CitiesResponse.builder().cities(citiesRepository.getAllCities()).build());
        log.info("HM :::" + resp.getStatus());
        log.info("json :::" + json);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(json);
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}