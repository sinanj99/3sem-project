package facades;

import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class ServerFacadeTest {

    private static EntityManagerFactory emf;
    private static ServerFacade facade;

    public ServerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = ServerFacade.getServerFacade(emf);
    }
    
    @Test
    public void fetchFromServer() {
        String[] servers = {"1", "2", "3", "4", "5"};
        assertEquals(5, facade.fetchFromServers(servers).size());
    }
}
