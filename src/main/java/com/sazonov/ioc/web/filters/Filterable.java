package com.sazonov.ioc.web.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Filterable {

    boolean filter(HttpServletRequest req, HttpServletResponse resp);
}
