/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sinanjasar
 */
public class City {
    @Schema(example = "Copenhagen", description = "Name of the city")
    private String cityName;
    @Schema(example = "Denmark", description = "Name of the country")
    private String country;
    @Schema(example = "Capital Region of Denmark", description = "State")
    private String state;
    @Schema(example = "Europe", description = "Name of the continent which the country is located in")
    private String continent;
    @Schema(example = "45", description = "Calling code")
    private String callingCode;
    @Schema(example = "14", description = "Local hour in that city")
    private String localHour;
    @Schema(example = "Danish kroner", description = "Name of the currency")
    private String currencyName;
    @Schema(example = "DKK", description = "Abbreviation of the currency")
    private String currencyAbb;
    @Schema(example = "kr.", description = "Symbol of the currency")
    private String currencySymbol;
    @Schema(example = "Øre", description = "Subunit of the currency")
    private String currencySubunit;
    @Schema(example = "Europe/Copenhagen", description = "Timezone region")
    private String timezoneRegion;
    @Schema(example = "CET", description = "Timezone short")
    private String timezoneShort;
    @Schema(example = "+0100", description = "Timezone offset")
    private String timezoneOffset;
    @Schema(example = "3600", description = "Timezone offset in seconds")
    private String timezoneOffsetSeconds;
    @Schema(example = "right", description = "The side of the road which cars drive on")
    private String roadInfoDriveSide; 
    @Schema(example = "km/h", description = "The speed unit")
    private String roadInfoSpeedUnit;
    @Schema(example = "138.25", description = "Qibla in degrees")
    private String qibla;
    @Schema(example = "50.15235", description = "Latitude")
    private Double lat;
    @Schema(example = "69.696969", description = "Longitude")
    private Double lon;
    private List<Weather> forecast;

    public City(JsonObject o, List<Weather> forecast, Map<String, String> opencageData) {
        this.cityName = o.get("city_name").getAsString();
        this.country = opencageData.get("country");
        this.state = opencageData.get("state");
        this.continent = opencageData.get("continent");
        this.callingCode = opencageData.get("callingcode");
        this.localHour = opencageData.get("currentHour");
        this.currencyName = opencageData.get("currency_name");
        this.currencyAbb = opencageData.get("currency_abbreviation");
        this.currencySymbol = opencageData.get("currency_symbol");
        this.currencySubunit = opencageData.get("currency_subunit");
        this.timezoneRegion = opencageData.get("timezone_region");
        this.timezoneShort = opencageData.get("timezone_short");
        this.timezoneOffset = opencageData.get("timezone_offset");
        this.timezoneOffsetSeconds = opencageData.get("timezone_offset_seconds");
        this.roadInfoDriveSide = opencageData.get("roadinfo_driveon");
        this.roadInfoSpeedUnit = opencageData.get("roadinfo_unit");
        this.qibla = opencageData.get("qibla");
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

    public String getState() {
        return state;
    }

    public String getContinent() {
        return continent;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public String getLocalHour() {
        return localHour;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencyAbb() {
        return currencyAbb;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getCurrencySubunit() {
        return currencySubunit;
    }

    public String getTimezoneRegion() {
        return timezoneRegion;
    }

    public String getTimezoneShort() {
        return timezoneShort;
    }

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public String getTimezoneOffsetSeconds() {
        return timezoneOffsetSeconds;
    }

    public String getRoadInfoDriveSide() {
        return roadInfoDriveSide;
    }

    public String getRoadInfoSpeedUnit() {
        return roadInfoSpeedUnit;
    }

    public String getQibla() {
        return qibla;
    }

    public List<Weather> getForecast() {
        return forecast;
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
