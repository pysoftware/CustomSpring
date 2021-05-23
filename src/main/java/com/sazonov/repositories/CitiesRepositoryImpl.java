package com.sazonov.repositories;

import com.sazonov.config.PostgreDbConfig;
import com.sazonov.enteties.City;
import com.sazonov.ioc.Component;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Component
public class CitiesRepositoryImpl implements CitiesRepository {
    @SneakyThrows
    @Override
    public List<City> getAllCities() {
        List<City> cities = new LinkedList<>();
        Connection connection = PostgreDbConfig.getInstance().getConnection();

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM cities");

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            long id = resultSet.getLong("id");

            cities.add(City.builder().id(id).name(name).build());
        }

        connection.close();
        return cities;
    }
}
