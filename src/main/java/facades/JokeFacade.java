package facades;

import dtos.JokeDTO;
import dtos.MemberDTO;
import entities.Joke;
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
public class JokeFacade {

    private static JokeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private JokeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<JokeDTO> getAllJokes() {
                EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Joke> query = em.createQuery("SELECT j FROM Joke j", Joke.class);
            List<Joke> jokes = query.getResultList();
            List<JokeDTO> jokeDTOs = new ArrayList();
            jokes.forEach((Joke joke) -> {
            jokeDTOs.add(new JokeDTO(joke));
            });
            return jokeDTOs;
        } finally {
            em.close();
        }
    }
   
    public JokeDTO getJokeById(int id) {
                        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Joke> query = em.createQuery("SELECT j FROM Joke j WHERE j.id LIKE :id", Joke.class);
            query.setParameter("id", id);
            Joke joke = query.getSingleResult();
            JokeDTO jokeDTO = new JokeDTO(joke);
            return jokeDTO;
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
            em.createQuery("DELETE from Joke").executeUpdate();
            em.persist(new Joke("How is Christmas like your job? You do all the work and the fat guy in the suit gets all the credit.", "Office"));
            em.persist(new Joke("An organization is like a tree full of monkeys, all on different limbs at different levels. The monkeys on top look down and see a tree full of smiling faces. The monkeys on the bottom look up and see nothing but assholes.", "Office"));
            em.persist(new Joke("Do not be racist; be like Mario. He's an Italian plumber, who was made by the Japanese, speaks English, looks like a Mexican, jumps like a black man, and grabs coins like a Jew!", "Technology"));
            em.persist(new Joke("Yo momma is so fat, when she sat on an iPod, she made the iPad!", "Technology"));
            em.persist(new Joke("Q: Why couldn't the blonde add 10 + 5 on a calculator? A: She couldn't find the 10 button", "Technology"));
            em.persist(new Joke("Q: What do computers eat for a snack? A: Microchips!", "Technology"));
            em.persist(new Joke("Q: Can a kangaroo jump higher than the Empire State Building? A: Of course. The Empire State Building can't jump.", "Animal"));
            em.persist(new Joke("What happens to a frog's car when it breaks down? It gets toad away.", "Animal"));
            em.persist(new Joke("Q: What did the duck say when he bought lipstick? A: Put it on my bill.", "Animal"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
