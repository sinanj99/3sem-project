/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.WeatherInfo;
import facades.WeatherFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author sinanjasar
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Weather API",
                version = "1.0",
                description = "API to get weather info.",
                contact = @Contact(name = "Obaydah & Sinan", email = "sinanjasar@live.dk")
        ),
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/3sem-project"
            ),
            @Server(
                    description = "Weather API",
                    url = "https://sinanjasar.dk/3sem-project"
            )

        }
)
@Path("weather")
public class WeatherResource {

    private static final WeatherFacade FACADE = WeatherFacade.getWeatherFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    
//    @Operation(summary = "Retrieves detailed information about the weather for a given city at a given date",
//            tags = {"Weather details"},
//            responses = {
//                @ApiResponse(
//                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherInfo.class))),
//                @ApiResponse(responseCode = "200", description = "The requested weather details were succesfully found"),
//                @ApiResponse(responseCode = "404", description = "The requested weather details could not be found")})
//    @Path("{city}/{date}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public void getWeatherDetails(@PathParam("city") String city, @PathParam("date") String date) throws IOException {
////        return FACADE.getWeatherDetails(city, date);
//    }
//    
    
    @Operation(summary = "Retrieves weather information for the upcoming 7 days (today included) in order",
            tags = {"Seven day forecast"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "200", description = "The requested weather data was succesfully found"),
                @ApiResponse(responseCode = "404", description = "The requested weather data could not be found")})
    @Path("{city}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<WeatherInfo> sevenDayForecast(@PathParam("city") String city) throws IOException {
        return FACADE.get7DayForecast(city);
    }
    
    @Operation(summary = "Retrieves the temperature for the next 24 hours",
            tags = {"Hourly forecast"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "200", description = "The requested weather data was succesfully found"),
                @ApiResponse(responseCode = "404", description = "The requested weather data could not be found")})
    @Path("hourly/{city}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, Integer> hourlyForecast(@PathParam("city") String city) throws IOException {
        return FACADE.getHourlyForecast(city);
        
    }

}
