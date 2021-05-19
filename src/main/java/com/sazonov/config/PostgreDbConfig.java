package com.sazonov.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public final class PostgreDbConfig {

//    private final String dbUri;
//    private final String dbUser;
//    private final String dbPassword;
//    private final String dbDriver;

    private static PostgreDbConfig instance;

    public static PostgreDbConfig getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PostgreDbConfig();
            return instance;
        }
        return instance;
    }

    @SneakyThrows
    private PostgreDbConfig() {
//        Dotenv dotenv = Dotenv.configure()
//                .directory(PostgreDbConfig.class.getResource("/.env").getPath())
//                .load();
//        dbUser = dotenv.get("db.uri");
//        dbPassword = dotenv.get("db.user");
//        dbUri = dotenv.get("db.password");
//        dbDriver = dotenv.get("db.driver");
//
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("org.postgresql.Driver" + " Driver is not found.");
        }
    }

    public Connection getConnection() throws SQLException {

        String url = "jdbc:postgresql://localhost:3309/controltest";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        return DriverManager.getConnection(url, props);
    }
}