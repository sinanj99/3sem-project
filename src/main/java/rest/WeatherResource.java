/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.WeatherFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

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
        tags = {
            @Tag(name = "server", description = "API related to person Info")

        },
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
@Path("server")
public class WeatherResource {

    private static final WeatherFacade FACADE = WeatherFacade.getServerFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public void getDataFromServers() {
//        return FACADE.fetchFromServers();
    }

}
