package facades;

import dtos.CarDTO;
import dtos.MemberDTO;
import entities.Car;
import entities.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CarFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CarFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CarDTO> getAllCars() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = query.getResultList();
            List<CarDTO> carDTOs = new ArrayList();
            cars.forEach((Car car) -> {
            carDTOs.add(new CarDTO(car));
            });
            return carDTOs;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        //Create emf pointing to the dev-database
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Car").executeUpdate();
            em.persist(new Car(1997, "Ford", "E350", 3000));
            em.persist(new Car(1999, "Chevy", "Venture", 4900));
            em.persist(new Car(2000, "Chevy", "Venture", 5000));
            em.persist(new Car(1996, "Jeep", "Grand Cherokee", 4799));
            em.persist(new Car(2005, "Volvo", "V70", 44799));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
