package rest;

import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entities.Car;

public class CarResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Car c1,c2;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
         //Don't forget this, if you called its counterpart in @BeforeAll
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }

    
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        c1 = new Car(2005, "Volvo", "V70", 44799);
        c2 = new Car(2000, "Chevy", "Venture", 5000);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2); 
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/groupmembers/all").then().statusCode(200);
    }

@Test
    public void serverIsRunning() {
        System.out.println("Testing is server UP");
        //Gherkin Syntax
        given().
                when().
                get("/cars/all").
                then().
                statusCode(200);
        //Hamcrest matcher
        given().
                when().
                get("/cars/all").
                then().assertThat().
                statusCode(200);
    }
@Test
    public void testGetAll() {
        given()
                .when().
                get("/cars/all")
                .then().
                assertThat()
                .body("size()", equalTo(2))
                .body("make",
                        hasItems("Volvo",
                                "Chevy"));
    }


    @Test
    public void contentType() {
        //Gherkin Syntax
        given().when().get("/cars/all").then().contentType(ContentType.JSON);
        //Hamcrest matcher
        given().when().get("/cars/all").then().assertThat().contentType(ContentType.JSON);
    }
    
       @Test
    public void testGetCarById() {
       
        given()
                .contentType("application/json")
                .get("/cars/" + c1.getId())
                .then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("make", equalTo(c1.getMake())) //Her m1.getTitle i stedet for getID
                .log()
                .body();

    }


}

