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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Harbour;
import entities.User;
import facades.BoatFacade;
import facades.HarbourFacade;
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
    private static final HarbourFacade HARBOUR_FACADE = HarbourFacade.getHarbourFacade(EMF);
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
        List<OwnerDTO> ownerDTO = null;
        try {
            ownerDTO = FACADE.getAllOwners();
        } catch (Exception e) {
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
    public String getHarbourBoats(@PathParam("harbourId") int harbourId) throws NotFoundException {
        List<BoatDTO> allBoatsInHarbour = null;
        if (harbourId != 0) {
            try {
                allBoatsInHarbour = BOAT_FACADE.getAllBoatsInHarbour(harbourId);
            } catch (Exception e) {
                throw new NotFoundException("Harbour could not be found");
            }
        } else {
            throw new NotFoundException("Missing harbour id");
        }
        if (allBoatsInHarbour != null) {
            return GSON.toJson(allBoatsInHarbour);
        } else {
            throw new NotFoundException("No boats in the harbour");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("Createboat")
    public String boatCreator(BoatDTO boatDTO) {
        BoatDTO boatResponseDTO = null;
        if (boatDTO != null) {
            try {
                System.out.println(boatDTO.toString());
                boatResponseDTO = BOAT_FACADE.createNewBoat(boatDTO);
            } catch (Exception e) {
                throw new NotFoundException("Boat could not be created");
            }
        } else {
            throw new NotFoundException("Missing boat");
        }
        if (boatResponseDTO != null) {
            return GSON.toJson(boatResponseDTO);
        } else {
            throw new NotFoundException("Boat could not be inserted");
        }

    }

    @PUT
    @Path("addboat/{harbourId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addBoatToHarbour(@PathParam("harbourId") int harbourId, String jsonString) throws NotFoundException {
        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
        int boatId = json.get("boatId").getAsInt();

        if (boatId != 0) {
            try {

                System.out.println("attempting to add boat");
                BOAT_FACADE.addBoatToHarbour(harbourId, boatId);
            } catch (Exception e) {
                throw new NotFoundException("Error");
            }
        } else {
            throw new NotFoundException("boat not found");
        }
    }
    @DELETE
    @Path("removeBoat/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String removeBoat(@PathParam("id") int boatId) throws NotFoundException {

        BoatDTO boatResponseDTO = null;

        if (boatId != 0) {
            try {
                boatResponseDTO = BOAT_FACADE.removeBoat(boatId);
            } catch (Exception e) {
                throw new NotFoundException("Boat could not be removed");
            }
        } else {
            throw new NotFoundException("Missing boat or id");
        }
        if (boatResponseDTO != null) {
            return GSON.toJson(boatResponseDTO);
        } else {
            throw new NotFoundException("Hobby could not be removed");
        }
    }

}