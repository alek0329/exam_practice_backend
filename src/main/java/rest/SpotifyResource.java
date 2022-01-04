package rest;

import facades.FacadeExample;
import facades.SpotifyFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Path("spotify")
public class SpotifyResource {

    @GET
    @Produces
    @Path("token")
    public String getTokenFromSpotify() throws IOException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        SpotifyFacade sf = SpotifyFacade.getSpotifyFacade(emf);

        String result = SpotifyFacade.getTokenFromSpotify();

        return result;
    }
}
