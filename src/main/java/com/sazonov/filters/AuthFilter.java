package com.sazonov.filters;

import com.sazonov.config.PostgreDbConfig;
import com.sazonov.ioc.web.filters.Filter;
import com.sazonov.ioc.web.filters.Filterable;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Filter(
        requestUriPath = "/*",
        order = 1
)
@Slf4j
public class AuthFilter implements Filterable {
    @SneakyThrows
    @Override
    public boolean filter(HttpServletRequest req, HttpServletResponse resp) {
        Optional<String> sessionId = findCookieByName(req.getCookies(), "JSESSIONID");
        if (!sessionId.isPresent()) {
            setNonAuthorizedResponseParameters(resp);
            return false;
        }
        log.info("SESSION ID:::" + sessionId.get());
        Connection dbConnection = PostgreDbConfig.getInstance().getConnection();
        PreparedStatement preparedStatement = dbConnection
                .prepareStatement("SELECT * FROM sessions where session_id = ?");
        preparedStatement.setString(1, sessionId.get());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            setNonAuthorizedResponseParameters(resp);
            return false;
        }
        dbConnection.close();
        return true;
    }

    private Optional<String> findCookieByName(Cookie[] cookies, String key) {
        if (Objects.isNull(cookies)) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    private void setNonAuthorizedResponseParameters(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(SC_UNAUTHORIZED);
        httpServletResponse.sendRedirect("/login");
    }
}
