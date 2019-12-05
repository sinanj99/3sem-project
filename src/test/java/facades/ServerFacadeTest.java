package facades;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class ServerFacadeTest {
    
    private static WeatherFacade facade;

    public ServerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
       facade = WeatherFacade.getWeatherFacade();
    }
    
    @Test
    public void fetchFromServer() {
        
    }
}
