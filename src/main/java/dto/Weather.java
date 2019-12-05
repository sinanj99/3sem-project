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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author sinanjasar
 */
public class Weather {

    @Schema(example = "2019-11-20", description = "Date (YYYY-MM-dd)")
    private String date;
    @Schema(example = "09:44", description = "Sunrise Time (24-hour clock)")
    private String sunrise;
    @Schema(example = "16:52", description = "Sunset Time (24-hour clock)")
    private String sunset;
    @Schema(example = "26", description = "Average Temperature (Celcius)")
    private float temp;
    @Schema(example = "90", description = "Probability of Precipitation (%)")
    private int pop;
    @Schema(example = "67", description = "Cloudiness (%)")
    private int clouds;
    @Schema(example = "90", description = "Humidity (%)")
    private int humidity;
    @Schema(example = "8.7", description = "Windspeed (m/s)")
    private float windSpeed;
    @Schema(example = "201", description = "Code representing weather conditions")
    private int weatherCode;
    @Schema(example = "t01d", description = "Icon-code representing weather conditions")
    private String weatherIcon;
    @Schema(example = "It's friggin cold", description = "A short description of the weather conditions")
    private String weatherDescription;

    public Weather(){
    }

    public Weather(JsonObject o, String timezone) {
        this.weatherDescription = o.get("weather").getAsJsonObject().get("description").getAsString();
        this.weatherIcon = o.get("weather").getAsJsonObject().get("icon").getAsString();
        this.weatherCode = o.get("weather").getAsJsonObject().get("code").getAsInt();
        this.date = o.get("valid_date").getAsString();
        
        
        this.sunrise = getTime(o.get("sunrise_ts").getAsLong(), timezone);
        this.sunset = getTime(o.get("sunset_ts").getAsLong(), timezone);
        
        
        this.temp = o.get("temp").getAsInt();
        this.pop = o.get("pop").getAsInt();
        this.clouds = o.get("clouds").getAsInt();
        this.humidity = o.get("rh").getAsInt();
        this.windSpeed = o.get("wind_spd").getAsFloat();
    }

    /**
     * Converts unix timestamp to HH:mm format
     * @param timeStamp
     * @return a String representation of current time in HH:mm format
     */
    private String getTime(long timeStamp, String timezone) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return Instant.ofEpochSecond(timeStamp).
                atZone(ZoneId.of(timezone)).toLocalTime().format(dtf);
    }


    public int getWeatherCode() {
        return weatherCode;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
    
    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDate() {
        return date;
    }


}
