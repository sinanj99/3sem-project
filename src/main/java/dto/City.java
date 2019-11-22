/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 *
 * @author sinanjasar
 */
public class City {
    @Schema(example = "Copenhagen", description = "Name of the city")
    private String cityName;
    @Schema(example = "DK", description = "Country code")
    private String country;
    @Schema(example = "50.15235", description = "Latitude")
    private Double lat;
    @Schema(example = "69.696969", description = "Longitude")
    private Double lon;
    private List<Weather> forecast;

    public City(JsonObject o, List<Weather> forecast) {
        this.cityName = o.get("city_name").getAsString();
        this.country = o.get("country_code").getAsString();
        this.lat = o.get("lat").getAsDouble();
        this.lon = o.get("lon").getAsDouble();
        this.forecast = forecast;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public List<Weather> getWeatherList() {
        return forecast;
    }

}
