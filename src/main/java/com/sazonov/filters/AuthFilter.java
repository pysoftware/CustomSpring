package com.sazonov.filters;

import com.sazonov.config.PostgreDbConfig;
import com.sazonov.ioc.web.filters.Filter;
import com.sazonov.ioc.web.filters.Filterable;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Optional;

@Filter(
        requestUriPath = "/*",
        order = 1
)
public class AuthFilter implements Filterable {
    @SneakyThrows
    @Override
    public boolean filter(HttpServletRequest req, HttpServletResponse resp) {
        Optional<String> sessionId = findCookieByName(req.getCookies(), "session_id");
        if (!sessionId.isPresent()) {
            return false;
        }
        Connection dbConnection = PostgreDbConfig.instance.getConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM sessions where session_id = ?");
        preparedStatement.setString(1, sessionId.get());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        dbConnection.close();
        return true;
    }

    private Optional<String> findCookieByName(Cookie[] cookies, String key) {
        return Arrays.stream(cookies)
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
