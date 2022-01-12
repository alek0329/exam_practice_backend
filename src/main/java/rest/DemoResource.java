package rest;

import DTO.HarbourDTOs.HarbourDTO;
import DTO.OwnerDTOS.OwnerDTO;
import DTO.boatDTOs.BoatDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import entities.Harbour;
import entities.User;
import facades.BoatFacade;
import facades.UserFacade;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final BoatFacade BOAT_FACADE = BoatFacade.getBoatFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;
//    private Gson gson = new Gson();

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {
        List <OwnerDTO> ownerDTO = null;
        try {
            ownerDTO = FACADE.getAllOwners();
        }catch (Exception e) {
            throw new NotFoundException("Error getting all persons");
        }
        if (ownerDTO != null && !ownerDTO.isEmpty()) {
            return GSON.toJson(ownerDTO);
        } else {
            throw new NotFoundException("Error getting all persons");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("harbour/{harbourId}")
    public String getHarbourBoats (@PathParam("harbourId")int harbourId) throws NotFoundException{
        List<BoatDTO> allBoatsInHarbour = null;
        if (harbourId != 0) {
            try {
                allBoatsInHarbour = BOAT_FACADE.getAllBoatsInHarbour(harbourId);
            } catch (Exception e) {
                throw new NotFoundException("Harbour could not be found");
            } }else{
                throw new NotFoundException("Missing harbour id");
            }
         if (allBoatsInHarbour != null){
            return GSON.toJson(allBoatsInHarbour);
        }else {
            throw new NotFoundException("No boats in the harbour");
        }
}
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("Createboat")
    public String boatCreator (BoatDTO boatDTO){
        BoatDTO boatResponseDTO = null;
        if (boatDTO != null){
            try {
                System.out.println(boatDTO.toString());
                boatResponseDTO = BOAT_FACADE.createNewBoat(boatDTO);
            }catch (Exception e){
                throw  new NotFoundException("Boat could not be created");
            }
        }else { throw new NotFoundException("Missing boat");
        }
        if (boatResponseDTO != null){
            return GSON.toJson(boatResponseDTO);
        }else{
            throw new NotFoundException("Boat could not be inserted");
        }

    }

}