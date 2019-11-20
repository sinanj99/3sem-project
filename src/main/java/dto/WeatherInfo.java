/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author sinanjasar
 */
public class WeatherInfo {

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

    public WeatherInfo() {
    }

    public WeatherInfo(JsonObject o) {
        this.date = o.get("valid_date").getAsString();
        this.sunrise = o.get("sunrise_ts").getAsString();
        this.sunset = o.get("sunset_ts").getAsString();
        this.temp = o.get("temp").getAsInt();
        this.pop = o.get("pop").getAsInt();
        this.clouds = o.get("clouds").getAsInt();
        this.humidity = o.get("rh").getAsInt();
        this.windSpeed = o.get("wind_spd").getAsFloat();
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

    @Override
    public String toString() {
        return "WeatherInfo{" + "date=" + date + ", sunrise=" + sunrise + ", sunset=" + sunset + ", temp=" + temp + ", pop=" + pop + ", clouds=" + clouds + ", humidity=" + humidity + ", windSpeed=" + windSpeed + '}';
    }
    
    
}
