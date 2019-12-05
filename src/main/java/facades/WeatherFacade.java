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
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    
    public static void main(String[] args) throws IOException {
        WeatherFacade wf = WeatherFacade.getWeatherFacade();
        
        //wf.getHourlyForecast("Copenhagen").forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
    }
    public List<String> getHourlyForecast(Double lat, Double lon) throws ProtocolException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/hourly?hours=24&lat=" + lat + "&lon=" + lon + "&key=" + WEATHERBIT);
        String jsonStr = retrieveData(url);
        JsonArray hours = new JsonParser().parse(jsonStr)
                .getAsJsonObject().get("data").getAsJsonArray();
        List<String> hourList = new LinkedList();
        
        for (JsonElement hour : hours) {
            hourList.add(hour.getAsJsonObject().get("timestamp_local").getAsString().split("T")[1].split(":")[0]+","+hour.getAsJsonObject().get("temp").getAsString());
        }
        return hourList;
    }
    /**
     * Returns a Map containing the temperature for the next 24 hours
     * @param city
     * @return Map<String, Integer>
     * @throws ProtocolException
     * @throws IOException
     */
    public List<String> getHourlyForecast(String city) throws ProtocolException, IOException {
        Map<String, String> opencageData = parseToCoordinates(city);
        return getHourlyForecast(Double.parseDouble(opencageData.get("lat")), Double.parseDouble(opencageData.get("lon")));
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
    public Map<String, String> parseToCoordinates(String city) throws MalformedURLException, IOException{
        URL url = new URL("https://api.opencagedata.com/geocode/v1/json?q="+city+"&language=en&key="+OPENCAGE);
        String jsonStr = retrieveData(url);
        JsonObject result = new JsonParser().parse(jsonStr).getAsJsonObject()
                .get("results").getAsJsonArray().get(0).getAsJsonObject();
        Map<String,String> opencageData = new LinkedHashMap();//annotations
        opencageData.put("lat",result.get("geometry").getAsJsonObject().get("lat").getAsString());
        opencageData.put("lon",result.get("geometry").getAsJsonObject().get("lng").getAsString());
        opencageData.put("continent",result.get("components").getAsJsonObject().get("continent").getAsString());
        opencageData.put("country",result.get("components").getAsJsonObject().get("country").getAsString());
        opencageData.put("state",result.get("components").getAsJsonObject().get("state").getAsString());
        opencageData.put("callingcode",result.get("annotations").getAsJsonObject().get("callingcode").getAsString());
        opencageData.put("currency_name",result.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("name").getAsString());
        opencageData.put("currency_abbreviation",result.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("iso_code").getAsString());
        opencageData.put("currency_symbol",result.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("symbol").getAsString());
        opencageData.put("currency_subunit",result.get("annotations").getAsJsonObject().get("currency").getAsJsonObject().get("subunit").getAsString());
        opencageData.put("timezone_region",result.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("name").getAsString());
        opencageData.put("timezone_short",result.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("short_name").getAsString());
        opencageData.put("timezone_offset",result.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("offset_string").getAsString());
        opencageData.put("timezone_offset_seconds",result.get("annotations").getAsJsonObject().get("timezone").getAsJsonObject().get("offset_sec").getAsString());
        opencageData.put("roadinfo_driveon",result.get("annotations").getAsJsonObject().get("roadinfo").getAsJsonObject().get("drive_on").getAsString());
        opencageData.put("roadinfo_unit",result.get("annotations").getAsJsonObject().get("roadinfo").getAsJsonObject().get("speed_in").getAsString());
        opencageData.put("qibla",result.get("annotations").getAsJsonObject().get("qibla").getAsString());
        
        final ZoneId zoneId = ZoneId.of(opencageData.get("timezone_region"));
        final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        opencageData.put("currentHour", zonedDateTime.toString().split("T")[1].split(":")[0]);
        
        return opencageData;
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
    public City get7DayForecast(Map<String, String> opencageData) throws MalformedURLException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/daily?days=7&key=" + WEATHERBIT + 
                "&lat=" + Double.parseDouble(opencageData.get("lat")) + "&lon=" + Double.parseDouble(opencageData.get("lon")));
        String jsonStr = retrieveData(url);
        List<Weather> weatherList = new ArrayList();
        JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
        JsonArray allDays = jsonObject.get("data").getAsJsonArray();;
        for (JsonElement day : allDays) {
            weatherList.add(new Weather(day.getAsJsonObject(), opencageData.get("timezone_region")));
        }
        return new City(jsonObject, weatherList, opencageData);
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
        Map<String, String> opencageData = parseToCoordinates(city);
        return get7DayForecast(opencageData);
    }

}
