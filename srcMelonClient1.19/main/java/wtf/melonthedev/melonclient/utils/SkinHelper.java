package wtf.melonthedev.melonclient.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

public class SkinHelper {


    public static String getSkinUrl(String uuid) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s", uuid)).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String reply2 = new String(Base64.getDecoder().decode(skin));
                //String skinUrl = reply2.split("\"textures\": { \n\"url\":\"")[1];
            } else {
                System.out.println("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
