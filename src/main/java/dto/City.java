/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 * @author sinanjasar
 */
public class City {

    @Schema(example = "Copenhagen", description = "Name of the city")
    private final String cityName;
    @Schema(example = "Denmark", description = "Name of the country")
    private final String country;
    @Schema(example = "Capital Region of Denmark", description = "State")
    private final String state;
    @Schema(example = "Europe", description = "Name of the continent which the country is located in")
    private final String continent;
    @Schema(example = "45", description = "Calling code")
    private final String callingCode;
    @Schema(example = "14", description = "Local hour in that city")
    private final String localHour;
    @Schema(example = "Danish kroner", description = "Name of the currency")
    private final String currencyName;
    @Schema(example = "DKK", description = "Abbreviation of the currency")
    private final String currencyAbb;
    @Schema(example = "kr.", description = "Symbol of the currency")
    private final String currencySymbol;
    @Schema(example = "Øre", description = "Subunit of the currency")
    private final String currencySubunit;
    @Schema(example = "Europe/Copenhagen", description = "Timezone region")
    private final String timezoneRegion;
    @Schema(example = "CET", description = "Timezone short")
    private final String timezoneShort;
    @Schema(example = "+0100", description = "Timezone offset")
    private final String timezoneOffset;
    @Schema(example = "3600", description = "Timezone offset in seconds")
    private final String timezoneOffsetSeconds;
    @Schema(example = "right", description = "The side of the road which cars drive on")
    private final String roadInfoDriveSide;
    @Schema(example = "km/h", description = "The speed unit")
    private final String roadInfoSpeedUnit;
    @Schema(example = "138.25", description = "Qibla in degrees")
    private final String qibla;
    @Schema(example = "50.15235", description = "Latitude")
    private final Double lat;
    @Schema(example = "69.696969", description = "Longitude")
    private final Double lon;
    private List<Weather> forecast;

    public City(JsonObject o) {
        this.cityName = o.get("components").getAsJsonObject().get("continent").getAsString();
        this.lat = o.get("geometry").getAsJsonObject().get("lat").getAsDouble();
        this.lon = o.get("geometry").getAsJsonObject().get("lng").getAsDouble();
        this.continent = o.get("components").getAsJsonObject().get("continent").getAsString();
        this.country = o.get("components").getAsJsonObject().get("country").getAsString();
        this.state = o.get("components").getAsJsonObject().get("state").getAsString();
        this.callingCode = o.get("annotations").getAsJsonObject().get("callingcode").getAsString();
        this.currencyName = o.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("name").getAsString();
        this.currencyAbb = o.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("iso_code").getAsString();
        this.currencySymbol = o.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("symbol").getAsString();
        this.currencySubunit = o.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("subunit").getAsString();
        this.timezoneRegion = o.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("name").getAsString();
        this.timezoneShort = o.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("short_name").getAsString();
        this.timezoneOffset = o.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("offset_string").getAsString();
        this.timezoneOffsetSeconds = o.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("offset_sec").getAsString();
        this.roadInfoDriveSide = o.get("annotations").getAsJsonObject().get("roadinfo").getAsJsonObject().get("drive_on").getAsString();
        this.roadInfoSpeedUnit = o.get("annotations").getAsJsonObject().get("roadinfo").getAsJsonObject().get("speed_in").getAsString();
        this.qibla = o.get("annotations").getAsJsonObject().get("qibla").getAsString();
        this.localHour = getLocalHour(this.timezoneRegion);
    }
    /**
     * Returns local hour
     * @return 
     */
    private String getLocalHour(String timezoneRegion) {
        final ZoneId zoneId = ZoneId.of(timezoneRegion);
        final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        return zonedDateTime.toString().split("T")[1].split(":")[0];
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

    public void setForecast(List<Weather> forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "City{" + "cityName=" + cityName + ", country=" + country + ", state=" + state + ", continent=" + continent + ", callingCode=" + callingCode + ", localHour=" + localHour + ", currencyName=" + currencyName + ", currencyAbb=" + currencyAbb + ", currencySymbol=" + currencySymbol + ", currencySubunit=" + currencySubunit + ", timezoneRegion=" + timezoneRegion + ", timezoneShort=" + timezoneShort + ", timezoneOffset=" + timezoneOffset + ", timezoneOffsetSeconds=" + timezoneOffsetSeconds + ", roadInfoDriveSide=" + roadInfoDriveSide + ", roadInfoSpeedUnit=" + roadInfoSpeedUnit + ", qibla=" + qibla + ", lat=" + lat + ", lon=" + lon + ", forecast=" + forecast + '}';
    }

}
