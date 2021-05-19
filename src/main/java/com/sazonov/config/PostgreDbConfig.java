package com.sazonov.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public final class PostgreDbConfig {

    private final String dbUri;
    private final String dbUser;
    private final String dbPassword;

    public static final PostgreDbConfig instance = new PostgreDbConfig();

    private PostgreDbConfig() {
        Dotenv dotenv = Dotenv.configure()
                .directory(PostgreDbConfig.class.getResource("/.env").getPath())
                .load();
        dbUser = dotenv.get("db.uri");
        dbPassword = dotenv.get("db.user");
        dbUri = dotenv.get("db.password");
        log.info(dotenv.get("db.uri"));
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/test";
        Properties props = new Properties();
        props.setProperty("db", "fred");
        props.setProperty("password", "secret");

        return DriverManager.getConnection(url, props);
    }

}