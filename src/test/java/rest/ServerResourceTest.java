package rest;

import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class ServerResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    final ZoneId zoneId = ZoneId.of("Europe/Copenhagen");
    final ZoneId zoneId2 = ZoneId.of("Europe/Moscow");
    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
    ZonedDateTime zonedDateTime2 = ZonedDateTime.ofInstant(Instant.now(), zoneId2);
    int time = Integer.valueOf(zonedDateTime.toString().split("T")[1].split(":")[0]);
    int time2 = Integer.valueOf(zonedDateTime2.toString().split("T")[1].split(":")[0]);

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    public void testForecast() throws Exception {
        given()
                .contentType("application/json")
                .get("/weather/forecast7/lyngby").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("forecast.size()", is(7))
                .and()
                .body("cityName", is("Kongens Lyngby"))
                .and()
                .body("localHour", is(String.valueOf(time)));

    }

    @Test
    public void testHourly() throws Exception {
        given()
                .contentType("application/json")
                .get("/weather/hourly/lyngby").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(24))
                .and()
                .body("[0].split(\",\")[0]", anyOf(equalTo(String.valueOf(time)),
                         equalTo(String.valueOf(time + 1))));
        given()
                .contentType("application/json")
                .get("/weather/hourly/moskva").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(24))
                .and()
                .body("[0].split(\",\")[0]", anyOf(equalTo(String.valueOf(time2)),
                         equalTo(String.valueOf(time2 + 1))));

    }
}
