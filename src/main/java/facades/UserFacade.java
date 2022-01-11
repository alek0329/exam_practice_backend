package facades;

import DTO.UserDTOS.CreateUserDTO;
import DTO.OwnerDTOS.OwnerDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Owner;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private UserFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password, user.getUserPass())) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public String createUser(String username, String password) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        EntityManager em = emf.createEntityManager();

        User user = new User(username, password);
        Role userRole;
        try {
            if (em.find(Role.class, "user") != null) {
                em.getTransaction().begin();
                userRole = em.find(Role.class, "user");
                user.addRole(userRole);
                em.persist(user);
                em.getTransaction().commit();
            }
            else {
                Role newUserRole = new Role("user");
                em.getTransaction().begin();
                em.persist(newUserRole);
                user.addRole(newUserRole);
                em.persist(user);
                em.getTransaction().commit();
            }


        } catch (Exception e) {
            createUserDTO.setStatus("failed");
            createUserDTO.setMessage(username + " already exists!");
            return gson.toJson(createUserDTO);
        } finally
        {
            em.close();
        }
        createUserDTO.setStatus("success");
        createUserDTO.setMessage(username + " created successfully!");
        return gson.toJson(createUserDTO);
    }

    public List<OwnerDTO> getAllOwners() {
        EntityManager em = emf.createEntityManager();
        List <Owner> allOwners;

        try {
            TypedQuery<Owner> tq = em.createQuery("SELECT o FROM Owner o", Owner.class);
            allOwners = tq.getResultList();
        }finally {
            em.close();
        }
        return OwnerDTO.getDTO(allOwners);
        }
}
