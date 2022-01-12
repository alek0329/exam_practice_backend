package facades;

import DTO.HarbourDTOs.HarbourDTO;
import DTO.boatDTOs.BoatDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Boat;
import entities.Harbour;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class BoatFacade {

    private static EntityManagerFactory emf;
    private static BoatFacade instance;
    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public BoatFacade() {
    }
    public static BoatFacade getBoatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    public List<BoatDTO> getAllBoatsInHarbour(int harbourId) {
        EntityManager em = emf.createEntityManager();
        List<Boat> boatsInHarbour;
        try{
            TypedQuery<Boat> tq = em.createQuery("select b from Boat b WHERE b.harbour.harbourId=:harbourId", Boat.class);
            tq.setParameter("harbourId",harbourId);
            boatsInHarbour = tq.getResultList();
        } finally{
            em.close();
        }
        return BoatDTO.getDTO(boatsInHarbour);
    }

    public BoatDTO createNewBoat(BoatDTO boatDTO){
        EntityManager em = emf.createEntityManager();
        Boat boat = null;

        try {
            em.getTransaction().begin();
            boat = new Boat(boatDTO.getBrand(), boatDTO.getName());
            em.persist(boat);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return new BoatDTO(boat);

    }
    public BoatDTO addBoatToHarbour(int harbourId, int boatId){
        EntityManager em = emf.createEntityManager();
        Boat boatToAdd = null;
        Harbour harbourToAdd = null;

        try {
            em.getTransaction().begin();
            System.out.println("getting boats");
            boatToAdd = em.find(Boat.class, boatId);
            harbourToAdd = em.find(Harbour.class, harbourId);
            System.out.println("adding boat");
            harbourToAdd.addBoat(boatToAdd);
            boatToAdd.setHarbour(harbourToAdd);
            em.merge(harbourToAdd);
            em.merge(boatToAdd);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            em.close();
        }
        return new BoatDTO(boatToAdd);
    }


}
