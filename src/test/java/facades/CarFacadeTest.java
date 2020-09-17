package facades;

import dtos.CarDTO;
import entities.Car;
import utils.EMF_Creator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CarFacadeTest {
    private static  EntityManagerFactory emf;
    private static  CarFacade facade;
    
    private Car c1;
    private Car c2;
    private Car c3;
    
    public CarFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CarFacade.getCarFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }
    
    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        c1 = new Car(1997, "Ford", "E350", 3000);
        c2 = new Car(1999, "Chevy", "Venture", 4900);
        c3 = new Car(2000, "Chevy", "Venture", 5000);
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Car").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
    }
     @Test
    public void getCarsByMake(){
        List<CarDTO> cars = facade.getCarsByMake(c2.getMake());
        assertThat(cars, hasItems(
        Matchers.<CarDTO>hasProperty("make", is ("Chevy"))
        
        )
        );
    }


    @Test
    public void testCarCount() {
        assertEquals(3,facade.getCarCount(),"Expects three cars in the database");
    }
    
    @Test
    public void testGetAllCars(){
         List<CarDTO> cars = facade.getAllCars();
        
        assertThat(cars, everyItem(hasProperty("model")));
        assertThat(cars, hasItems( // or contains or containsInAnyOrder 
                Matchers.<CarDTO>hasProperty("model", is("E350")),
                Matchers.<CarDTO>hasProperty("model", is("Venture"))
        )
        );
    }

   
    
  



}

