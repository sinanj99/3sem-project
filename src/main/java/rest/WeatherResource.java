/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;
import dto.City;
import facades.WeatherFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import java.io.IOException;
import java.util.HashMap;
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
                title = "GOATED Weather API",
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
    @Operation(summary = "Retrieves weather information for the upcoming 7 days (today included) in order",
            tags = {"Seven day forecast"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "200", description = "The requested weather data was succesfully found"),
                @ApiResponse(responseCode = "404", description = "The requested weather data could not be found")})
    @Path("/forecast7/{city}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public City get7DayForecast(@PathParam("city") String city) throws IOException {
        return FACADE.get7DayForecast(city);
    }

    @Operation(summary = "Retrieves weather information for the upcoming 7 days (today included) in order",
            tags = {"Seven day forecast"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "200", description = "The requested weather data was succesfully found"),
                @ApiResponse(responseCode = "404", description = "The requested weather data could not be found")})
    @Path("/forecast7/{lat},{lon}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public City get7DayForecast(@PathParam("lat") String lat, @PathParam("lon") String lon) throws IOException {
        Map<String,String> latlon = new HashMap();
        latlon.put("lat",lat);
        latlon.put("lon",lon);
        return FACADE.get7DayForecast(latlon);
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
    public List<String> hourlyForecast(@PathParam("city") String city) throws IOException {
        return FACADE.getHourlyForecast(city);
    }
    @Operation(summary = "Retrieves the temperature for the next 24 hours",
            tags = {"Hourly forecast"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "200", description = "The requested weather data was succesfully found"),
                @ApiResponse(responseCode = "404", description = "The requested weather data could not be found")})
    @Path("hourly/{lat},{lon}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> hourlyForecast(@PathParam("lat") Double lat,@PathParam("lon") Double lon ) throws IOException {
        return FACADE.getHourlyForecast(lat,lon);
    }

}
