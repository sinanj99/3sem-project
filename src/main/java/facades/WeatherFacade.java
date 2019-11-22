/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.City;
import dto.Weather;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author sinanjasar
 */
public class WeatherFacade {

    private static WeatherFacade instance;
    private final String WEATHERBIT = getWeatherbit();
    private final String OPENCAGE = getOpencage();

    //Private Constructor to ensure Singleton
    private WeatherFacade() {
    }

    /**
     *
     * @return an instance of this facade class.
     */
    public static WeatherFacade getWeatherFacade() {
        if (instance == null) {
            instance = new WeatherFacade();
        }
        return instance;
    }

    private String getWeatherbit() {
        return System.getenv("WEATHERBIT");
    }
    private String getOpencage() {
        return System.getenv("OPENCAGE");
    }
    public Map<String, Integer> getHourlyForecast(Double lat, Double lon) throws ProtocolException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/hourly?hours=24&lat=" + lat + "&lon=" + lon + "&key=" + WEATHERBIT);
        String jsonStr = retrieveData(url);
        Map<String, Integer> hourlyTempMap = new LinkedHashMap<>();
        JsonArray hours = new JsonParser().parse(jsonStr)
                .getAsJsonObject().get("data").getAsJsonArray();
        for (JsonElement hour : hours) {
            String key = hour.getAsJsonObject().get("datetime").getAsString();
            hourlyTempMap.put(key.substring(key.length() - 2), hour.getAsJsonObject().get("temp").getAsInt());
        }
        return hourlyTempMap;
    }
    /**
     * Returns a Map containing the temperature for the next 24 hours
     * @param city
     * @return Map<String, Integer>
     * @throws ProtocolException
     * @throws IOException
     */
    public Map<String, Integer> getHourlyForecast(String city) throws ProtocolException, IOException {
        Map<String, Double> coordinates = parseToCoordinates(city);
        return getHourlyForecast(coordinates.get("lat"), coordinates.get("lon"));
    }

    /**
     * Returns json from the specified URL
     * @param url
     * @return json String
     * @throws ProtocolException
     * @throws IOException
     */
    private String retrieveData(URL url) throws ProtocolException, IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        String jsonStr = "";
        try (Scanner scan = new Scanner(con.getInputStream())) {
            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
            }
        }
        return jsonStr;
    }
    
    /**
     * Forward geocoding
     * @param city
     * @return 
     * @throws MalformedURLException
     * @throws IOException 
     */
    public Map<String, Double> parseToCoordinates(String city) throws MalformedURLException, IOException{
        URL url = new URL("https://api.opencagedata.com/geocode/v1/json?q="+city+"&key="+OPENCAGE);
        String jsonStr = retrieveData(url);
        JsonObject geometry = new JsonParser().parse(jsonStr).getAsJsonObject()
                .get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject();
        Map<String,Double> coordinates = new HashMap();
        coordinates.put("lat",geometry.get("lat").getAsDouble());
        coordinates.put("lon",geometry.get("lng").getAsDouble());
        return coordinates;
    }
    
    /**
     * Returns a City object containing city info, and a list of 7 Weather objects representing
     * the weather for the week.
     * @param lat
     * @param lon
     * @return JsonArray
     * @throws java.net.MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public City get7DayForecast(Double lat, Double lon) throws MalformedURLException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/daily?days=7&key=" + WEATHERBIT + "&lat=" + lat + "&lon=" + lon);
        String jsonStr = retrieveData(url);
        List<Weather> weatherList = new ArrayList();
        JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
        JsonArray allDays = jsonObject.get("data").getAsJsonArray();
        for (JsonElement day : allDays) {
            weatherList.add(new Weather(day.getAsJsonObject()));
        }
        return new City(jsonObject, weatherList);
    }
    /**
     * Returns a City object containing city info, and a list of 7 Weather objects representing
     * the weather for the week.
     * @param city
     * @return JsonArray
     * @throws java.net.MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public City get7DayForecast(String city) throws MalformedURLException, IOException {
        Map<String, Double> coordinates = parseToCoordinates(city);
        return get7DayForecast(coordinates.get("lat"),coordinates.get("lon"));
    }

    public static void main(String[] args) {

    }

}
