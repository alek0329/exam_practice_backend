package facades;

import DTO.HarbourDTOs.HarbourDTO;
import DTO.boatDTOs.BoatDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Boat;
import entities.Harbour;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HarbourFacade {

    private static EntityManagerFactory emf;
    private static HarbourFacade instance;
    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public HarbourFacade() {
    }

    public static HarbourFacade getHarbourFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }

}


