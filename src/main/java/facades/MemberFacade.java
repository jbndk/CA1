package facades;

import dtos.MemberDTO;
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
public class MemberFacade {

    private static MemberFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MemberFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MemberFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MemberFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<MemberDTO> getAllMembers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
            List<Member> members = query.getResultList();
            List<MemberDTO> memberDTOs = new ArrayList();
            members.forEach((Member member) -> {
            memberDTOs.add(new MemberDTO(member));
            });
            return memberDTOs;
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
            em.createQuery("DELETE from Member").executeUpdate();
            em.persist(new Member("Jonas Sthur Brøchner-Nielsen", "cph-jb373", 26));
            em.persist(new Member("Claes Lindholm", "cph-cl303", 53));
            em.persist(new Member("Anne-Maj Andersen", "cph-al221", 35));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
