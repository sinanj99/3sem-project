/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author sinanjasar
 */
public class WeatherInfo {
    
    @Schema (example = "Copenhagen", description = "City")
    private String city;
    @Schema(example = "2019-11-20", description = "Date (YYYY-DD-MM)")
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
    private Map<String, Integer> hourlyTemp;

    public WeatherInfo() {
    }

    public WeatherInfo(JsonObject o) {
        this.city = o.get("city").getAsString();
        this.date = o.get("valid_date").getAsString();
        this.sunrise = getTime(o.get("sunrise_ts").getAsLong());
        this.sunset = getTime(o.get("sunset_ts").getAsLong());
        this.temp = o.get("temp").getAsInt();
        this.pop = o.get("pop").getAsInt();
        this.clouds = o.get("clouds").getAsInt();
        this.humidity = o.get("rh").getAsInt();
        this.windSpeed = o.get("wind_spd").getAsFloat();
    }

    /**
     * Converts timestamp to HH:mm format
     * @param timeStamp
     * @return a String representation of current time in HH:mm format
     */
    private String getTime(long timeStamp) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return Instant.ofEpochSecond(timeStamp).
                atZone(ZoneOffset.UTC).toLocalTime().format(dtf);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Map<String, Integer> getHourlyTemp() {
        return hourlyTemp;
    }

    public void setHourlyTemp(Map<String, Integer> hourlyTemp) {
        this.hourlyTemp = hourlyTemp;
    }
    
    

    @Override
    public String toString() {
        return "WeatherInfo{" + "date=" + date + ", sunrise=" + sunrise + ", sunset=" + sunset + ", temp=" + temp + ", pop=" + pop + ", clouds=" + clouds + ", humidity=" + humidity + ", windSpeed=" + windSpeed + '}';
    }
}
