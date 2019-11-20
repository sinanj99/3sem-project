/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author sinanjasar
 */
public class Weather {

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
   
    public Weather() {
    }

    public Weather(String sunset, String sunrise, float temp, int pop, int clouds, int humidity, float windSpeed) {
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.temp = temp;
        this.pop = pop;
        this.clouds = clouds;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
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

}
