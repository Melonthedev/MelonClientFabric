package wtf.melonthedev.melonclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.ChatFormatting;
import net.minecraft.data.Main;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.utils.HttpUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ApiWrapper {

    public static String URL = Client.apiUrl; //TODO: Add Dev field to change api url locally
    //public static String URL = "http://localhost:5023/api/";


    public static void init() {
        handleSendAlivePackets();
        loadCredits();
    }

    public static void handleSendAlivePackets() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                CompletableFuture.runAsync(() -> {
                    UUID uuid = Client.getMinecraft().getUser().getProfileId();
                    if (uuid == null) return;
                    handleAccount(uuid, () -> {
                        HttpUtils.executePostAsync(URL + "user/" + uuid + "/SendAlive");
                        Client.getInstance().getLOGGER().info("SENT to " + URL + "user/" + uuid + "/SendAlive");
                    });
                });
            }
        }, 0, 90000);
    }

    public static void handleNewMelonClientUser() {
        CompletableFuture.runAsync(() -> {
            String username = Client.getMinecraft().getUser().getName();
            //HttpUtils.executePostAsync(URL + "user/" + username + "/new"));
        });
    }

    public static void loadCredits() {
        HttpUtils.executeGetAsync("https://melonclient.melonthedev.wtf/credits.txt", (response) -> {
            if (response == null) return;
            String[] creditsStrings = response.split(System.lineSeparator());
            Component[] credits = new Component[creditsStrings.length];
            for (int i = 0; i < creditsStrings.length; i++) {
                String s = creditsStrings[i];
                if (s == null) continue;
                Component component = s.isBlank() ? Component.empty() : Component.literal(s);
                if (s.startsWith("!Y!"))
                    component = Component.literal(s.substring(3)).withStyle(ChatFormatting.YELLOW);
                else if (s.startsWith("!G!"))
                    component = Component.literal(s.substring(3)).withStyle(ChatFormatting.GRAY);
                credits[i] = component;
            }
            Client.getInstance().credits = credits;
        });
    }

    /*public static int getCapeCount(UUID uuid) {
        handleAccount(uuid);
        String response = HttpUtils.executeGet(getCapeURL() + uuid + "/count/");
        if (response == null) return 0;
        return Integer.parseInt(response);
    }
    public static void getCapeCountAsync(UUID uuid, ResponseCallbackInt callback) {
        handleAccount(uuid);
        HttpUtils.executeGetAsync(getCapeURL() + uuid + "/count/", response1 -> callback.onResponse(response1 == null ? 0 : Integer.parseInt(response1)));
    }*/

    /*public static JsonObject getCape(UUID uuid, int id) {
        handleAccount(uuid);
        String response = HttpUtils.executeGet(getCapeURL() + uuid + "/" + id);
        if (response == null) return null;
        JsonElement json = new JsonParser().parse(response);
        System.out.println("R: " + response + " U: " + uuid + " J: " + json);
        if (json == null) return null;
        if (json.toString().equalsIgnoreCase("null")) return null;
        return json.getAsJsonObject();
    }*/

    public static void getCapeAsync(UUID uuid, int id, ResponseCallbackJson callback) {
        handleAccount(uuid, () -> HttpUtils.executeGetAsync(getCapeURL() + uuid + "/" + id, (response) -> {
            if (response == null) return;
            JsonElement json = new JsonParser().parse(response);
            if (Client.isDevModeEnabled()) Client.getInstance().getLOGGER().info("R: " + response + " U: " + uuid + " J: " + json);
            if (json == null || json.toString().equalsIgnoreCase("null")) return;
            callback.onResponse(json.getAsJsonObject());
        }));
    }

    /*public static List<Integer> getCapeIds(UUID uuid) {
        handleAccount(uuid);
        String response = HttpUtils.executeGet(getCapeURL() + uuid);
        if (response == null) return new ArrayList<>();
        JsonElement json = new JsonParser().parse(response);
        if (json == null) return new ArrayList<>();
        if (json.toString().equalsIgnoreCase("null")) return new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray())
            ids.add(e.getAsJsonObject().get("capeId").getAsInt());
        return ids;
    }*/

    public static void getCapeIdsAsync(UUID uuid, ResponseCallbackList callback) {
        handleAccount(uuid, () -> HttpUtils.executeGetAsync(getCapeURL() + uuid, (response) -> {
            if (response == null) return;
            JsonElement json = new JsonParser().parse(response);
            if (json == null || json.toString().equalsIgnoreCase("null")) return;
            List<Integer> ids = new ArrayList<>();
            for (JsonElement e : json.getAsJsonArray())
                ids.add(e.getAsJsonObject().get("capeId").getAsInt());
            callback.onResponse(ids);
        }));
    }

    /*public static boolean isAnimated(UUID uuid, int id) {
        handleAccount(uuid);
        JsonObject json = getCape(uuid, id);
        if (json == null) return false;
        return json.get("isAnimated").getAsBoolean();
    }*/

    public static void isAnimatedAsync(UUID uuid, int id, ResponseCallbackBool callback) {
        handleAccount(uuid, () -> getCapeAsync(uuid, id, cape -> {
            if (cape == null || !cape.has("isAnimated")) {
                callback.onResponse(false);
                return;
            }
            callback.onResponse(cape.get("isAnimated").getAsBoolean());
        }));
    }


    /*public static int getSelectedCape(UUID uuid) {
        handleAccount(uuid);
        String response = HttpUtils.executeGet(getCapeURL() + uuid + "/selected/id");
        if (response == null) return 0;
        return Integer.parseInt(response);
    }*/

    public static void getSelectedCapeIdAsync(UUID uuid, ResponseCallbackInt callback) {
        handleAccount(uuid, () -> HttpUtils.executeGetAsync(getCapeURL() + uuid + "/selected/id", response ->
                callback.onResponse(response == null ? 0 : Integer.parseInt(response))));
    }

    /*public static void setSelectedCape(UUID uuid, int cape) {
        handleAccount(uuid);
        HttpUtils.executePostAsync(getCapeURL() + uuid + "/select/" + cape);
    }*/
    public static void setSelectedCapeAsync(UUID uuid, int cape) {
        handleAccount(uuid, () -> HttpUtils.executePostAsync(getCapeURL() + uuid + "/select/" + cape));
    }

    /*public static boolean hasCapeWithId(UUID uuid, int id) {
        handleAccount(uuid);
        String response = HttpUtils.executeGet(getCapeURL() + uuid + "/" + id);
        return response != null && !response.equals("");
    }*/

    public static void hasCapeWithIdAsync(UUID uuid, int id, ResponseCallbackBool callback) {
        handleAccount(uuid, () -> HttpUtils.executeGetAsync(getCapeURL() + uuid + "/" + id, response -> callback.onResponse(response != null && !response.equals(""))));
    }

    public static String getURL() {
        return URL;
    }

    public static String getCapeURL() {
        return URL + "cape/";
    }

    /*public static void handleAccount(UUID uuid) {
        handleAccount(uuid, () -> {});
    }*/

    public static void handleAccount(UUID uuid, ResponseCallback callback) {
        HttpUtils.executeGetAsync(getURL() + "User/userExists/" + uuid.toString(), response -> {
            if (response == null) return;
            boolean exists = response.equalsIgnoreCase("true");
            if (!exists)
                HttpUtils.executePostAsync(getURL() + "User/CreateUser/?uuid=" + uuid);
            callback.onResponse();
        });
    }


    public interface ResponseCallbackInt {
        void onResponse(Integer response);
    }

    public interface ResponseCallbackString {
        void onResponse(String response);
    }

    public interface ResponseCallbackJson {
        void onResponse(JsonObject response);
    }

    public interface ResponseCallbackBool {
        void onResponse(Boolean response);
    }
    public interface ResponseCallback {
        void onResponse();
    }
    public interface ResponseCallbackList {
        void onResponse(List<Integer> response);
    }
}
