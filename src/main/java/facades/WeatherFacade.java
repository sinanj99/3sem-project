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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author sinanjasar
 */
public class WeatherFacade {

    private static WeatherFacade instance;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final static String APIKEY = "50f8d14b7d8a4c64ba1d5c32c9a3aae4";

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


    private static String fetchHourlyInfo(String city) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/hourly?hours=2&city="+city+"&key="+APIKEY);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        String jsonStr = "";
        try (Scanner scan = new Scanner(con.getInputStream())) {
            while (scan.hasNext()) {

                jsonStr += scan.nextLine()+"\n";
            }
        }
        return jsonStr;
    }

    public static void main(String[] args) throws IOException {
        /*for(String s : fetchHourlyInfo("Copenhagen").split(",")){
            System.out.println(s);
        }*/
        System.out.print(fetchHourlyInfo("Copenhagen"));
    }

//    /**
//     * Should call fetchFromServer method on multiple servers
//     * 
//     * @param servers
//     * @return A list containing all json objects returned by servers.
//     */
//    public List<Post> fetchFromServers(String[] servers) {
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        Queue<Future> futureList = new LinkedList();
//        List<Post> posts = new ArrayList();
//        for (String s : servers) {
//            Future<Post> future = executor.submit(() -> {
//                JsonObject jsonObj = new JsonParser().parse(fetchFromServer(s))
//                        .getAsJsonObject();
//                return new Post(jsonObj.get("id").getAsInt(),
//                        jsonObj.get("title").getAsString(), jsonObj.get("body").getAsString());
//            });
//            futureList.add(future);
//        }
//        while (!futureList.isEmpty()) {
//            Future<Post> f = futureList.poll();
//            if (f.isDone()) {
//                try {
//                    posts.add(f.get());
//                } catch (InterruptedException | ExecutionException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            } else {
//                futureList.add(f);
//            }
//        }
//        return posts;
//    }
    /**
     * Returns a list of seven WeatherInfo objects in order
     * @param city
     * @return JsonArray
     * @throws ProtocolException
     * @throws IOException
     */
    public List<WeatherInfo> get7DayForecast(String city) throws ProtocolException, IOException {
        URL url = new URL("https://api.weatherbit.io/v2.0/forecast/daily?days=7&key=" + APIKEY + "&city=" + city);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        String jsonStr = "";
        try (Scanner scan = new Scanner(con.getInputStream())) {
            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
            }
        }
        List<WeatherInfo> weatherList = new ArrayList();
        JsonArray allDays = new JsonParser().parse(jsonStr)
                .getAsJsonObject().get("data").getAsJsonArray();
        for (JsonElement day : allDays) {
            weatherList.add(new WeatherInfo(day.getAsJsonObject()));
        }
        return weatherList;
    }
}
