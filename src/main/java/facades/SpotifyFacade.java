package facades;


import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.eclipse.yasson.YassonJsonb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class SpotifyFacade {
    private static SpotifyFacade instance;
    private static EntityManagerFactory emf;
    private static String expiresIn;
    private static String accessToken;
    private static String refreshToken;
    private static Gson gson = new Gson();


    private SpotifyFacade() {
    }

    public static SpotifyFacade getSpotifyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SpotifyFacade();
        }
        return instance;
    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public static String getTokenFromSpotify() throws IOException {
        String clientID = Secrets.clientID;
        String clientSecret = Secrets.clientSecret;
        String Response = null;
        String tokenURL = "https://accounts.spotify.com/api/token";

        URL url = new URL(tokenURL);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

        String data = "grant_type=client_credentials&client_id=" + clientID + "&client_secret=" + clientSecret + "";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        BufferedReader Lines = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String currentLine = Lines.readLine();
        String response = "";
        while (currentLine != null) {
            response = currentLine;
            currentLine = Lines.readLine();
        }

        accessToken = String.valueOf(JsonParser.parseString(response).getAsJsonObject().getAsJsonPrimitive("access_token"));
        expiresIn = String.valueOf(JsonParser.parseString(response).getAsJsonObject().getAsJsonPrimitive("expires_in"));
        accessToken = accessToken.substring(1, accessToken.length() - 1);
        http.disconnect();


        return response;

    }
}