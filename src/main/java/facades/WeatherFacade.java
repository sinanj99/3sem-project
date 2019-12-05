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
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

    /**
     * Takes city and returns list of hours
     *
     * @param city
     * @return
     * @throws java.net.MalformedURLException
     */
    public List<String> hoursByCity(String city) throws MalformedURLException, IOException {
        Map<String, Double> coords = parseToCoordinates(city);
        return getHours(coords.get("lat"), coords.get("lon"));
    }

    /**
     * Takes long lat and returns list of hours
     *
     * @param lat
     * @param lon
     * @return
     * @throws java.net.MalformedURLException
     */
    public List<String> hoursByCoords(Double lat, Double lon) throws MalformedURLException, IOException {
        return getHours(lat, lon);
    }

    public List<String> getHours(Double lat, Double lon) throws ProtocolException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/hourly?hours=24&lat=" + lat + "&lon=" + lon + "&key=" + WEATHERBIT);
        String jsonStr = retrieveData(url);
        JsonArray hours = new JsonParser().parse(jsonStr)
                .getAsJsonObject().get("data").getAsJsonArray();
        List<String> hourList = new LinkedList();
        for (JsonElement hour : hours) {
            hourList.add(hour.getAsJsonObject().get("timestamp_local").getAsString().split("T")[1].split(":")[0] + "," + hour.getAsJsonObject().get("temp").getAsString());
        }
        return hourList;
    }

    /**
     * Takes city, returns coordinates
     *
     * @param city
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    private Map<String, Double> parseToCoordinates(String city) throws MalformedURLException, IOException {
        URL url = new URL("https://api.opencagedata.com/geocode/v1/json?q=" + city + "&language=en&key=" + OPENCAGE);
        String jsonStr = retrieveData(url);
        JsonObject result = new JsonParser().parse(jsonStr).getAsJsonObject()
                .get("results").getAsJsonArray().get(0).getAsJsonObject();
        Map<String, Double> opencageData = new LinkedHashMap();
        opencageData.put("lat", result.get("geometry").getAsJsonObject().get("lat").getAsDouble());
        opencageData.put("lon", result.get("geometry").getAsJsonObject().get("lng").getAsDouble());
        return opencageData;
    }

    /**
     * Returns json from the specified URL
     *
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
     * Takes city name and returns city object.
     *
     * @param cityName
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public City forwardCity(String cityName) throws MalformedURLException, IOException {
        URL url = new URL("https://api.opencagedata.com/geocode/v1/json?q=" + cityName + "&language=en&key=" + OPENCAGE);
        return getCity(url);
    }

    /**
     * Takes lat long and returns city object
     *
     * @param lat
     * @param lon
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public City reverseCity(Double lat, Double lon) throws MalformedURLException, IOException {
        URL url = new URL("https://api.opencagedata.com/geocode/v1/json?q=" + lat + "%2C+" + lon + "&language=en&key=" + OPENCAGE);
        return getCity(url);
    }

    /**
     * Takes URL and gets city
     *
     * @param url
     * @return
     * @throws IOException
     */
    private City getCity(URL url) throws IOException {
        String jsonStr = retrieveData(url);
        JsonObject result = new JsonParser().parse(jsonStr).getAsJsonObject()
                .get("results").getAsJsonArray().get(0).getAsJsonObject();
        City city = new City(result);
        city.setForecast(get7DayForecast(city.getLat(), city.getLon()));
        return city;
    }

    /**
     * Returns a City object containing city info, and a list of 7 Weather
     * objects representing the weather for the week.
     *
     * @param lat
     * @param lon
     * @return JsonArray
     * @throws java.net.MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    private List<Weather> get7DayForecast(Double lat, Double lon) throws MalformedURLException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/daily?days=7&key=" + WEATHERBIT
                + "&lat=" + lat + "&lon=" + lon);
        String jsonStr = retrieveData(url);
        List<Weather> weatherList = new ArrayList();
        JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
        JsonArray allDays = jsonObject.get("data").getAsJsonArray();
        String timezone = jsonObject.get("timezone").getAsString();
        for (JsonElement day : allDays) {
            weatherList.add(new Weather(day.getAsJsonObject(), timezone));
        }
        return weatherList;
    }
}
