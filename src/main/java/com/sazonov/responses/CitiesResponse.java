package com.sazonov.responses;

import com.sazonov.enteties.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitiesResponse {
    private List<City> cities;
}
