/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.WeatherInfo;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    private final String APIKEY = "50f8d14b7d8a4c64ba1d5c32c9a3aae4";

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

    private String getApiKey() {
        return System.getenv("API_KEY");
    }

    /**
     * Returns a Map containing the temperature for the next 24 hours
     *
     * @param city
     * @return Map<String, Integer>
     * @throws ProtocolException
     * @throws IOException
     */
    public Map<String, Integer> getHourlyForecast(String city) throws ProtocolException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/hourly?hours=24&city=" + city + "&key=" + APIKEY);
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
     * Returns a list of 7 WeatherInfo objects representing the weather for the
     * upcoming week.
     *
     * @param city
     * @return JsonArray
     * @throws java.net.MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public List<WeatherInfo> get7DayForecastCity(String city) throws MalformedURLException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/daily?days=7&key=" + APIKEY + "&city=" + city);
        String jsonStr = retrieveData(url);
        List<WeatherInfo> weatherList = new ArrayList();

        JsonObject jsonStrParsedToObject = new JsonParser().parse(jsonStr).getAsJsonObject();
        JsonArray allDays = jsonStrParsedToObject.get("data").getAsJsonArray();

        /*
            Ved godt vi bare kan bruge parameteren city, men hvis brugeren 
            fx. taster copenhagen, CoPenHaGEN, så vil det blive vist på den måde i forecast viewet
         */
        String city_ = jsonStrParsedToObject.get("city_name").getAsString();

        for (JsonElement day : allDays) {
            day.getAsJsonObject().addProperty("city", city_);
            weatherList.add(new WeatherInfo(day.getAsJsonObject()));
        }
        return weatherList;

    }

    /**
     * Returns a list of 7 WeatherInfo objects representing the weather for the
     * upcoming week.
     *
     * @param lat
     * @param lon
     * @return JsonArray
     * @throws java.net.MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public List<WeatherInfo> get7DayForecastCord(String lat, String lon) throws MalformedURLException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/daily?days=7&key=" + APIKEY + "&lat=" + lat + "&lon=" + lon);
        String jsonStr = retrieveData(url);
        List<WeatherInfo> weatherList = new ArrayList();
        JsonArray allDays = new JsonParser().parse(jsonStr)
                .getAsJsonObject().get("data").getAsJsonArray();
        for (JsonElement day : allDays) {
            weatherList.add(new WeatherInfo(day.getAsJsonObject()));
        }
        return weatherList;

    }

    public static void main(String[] args) {
        System.out.println(System.getenv("API_KEY"));
    }

}
