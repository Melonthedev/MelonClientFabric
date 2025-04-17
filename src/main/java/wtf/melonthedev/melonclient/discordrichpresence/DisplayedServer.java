package wtf.melonthedev.melonclient.discordrichpresence;

import wtf.melonthedev.melonclient.Client;

import java.util.List;

public enum DisplayedServer {

    HYPIXEL("hypixel.net", "hypixel"),
    GOMMEHD("gommehd.net", "gomme"),
    HGLABOR("hglabor.de", "hglabor"),
    MCSURVIVALPROJEKT("mcsurvivalprojekt.de", "survivalprojekt"),
    TWOBTWOT("2b2t.org", "2b2t"),
    GRIEFERGAMES("griefergames.net", "griefergames"),
    LOCALHOST("localhost", "localhost"),
    LOCALHOST2("127.0.0.1", "localhost"),
    BRIDGERLAND("briger.land", "bridgerland");

    private final String ip;
    private final String imageKey;

    DisplayedServer(String ip, String imageKey) {
        this.ip = ip;
        this.imageKey = imageKey;
    }

    public String getIp() {
        return ip;
    }
    public String getImageKey() {
        return imageKey;
    }

    public static void displayServer(String ip) {
        for (DisplayedServer ds : DisplayedServer.values()) {
            if (ip.contains(ds.getIp())) {
                displayServer(ds, ip);
                return;
            }
        }
        displayCustomServer(ip);
    }
    private static void displayServer(DisplayedServer ds, String fullIp) {
        Client.getInstance().getDiscordRP().updateMultiplayerServer(fullIp, ds.getImageKey());
    }
    private static void displayCustomServer(String ip) {
        Client.getInstance().getDiscordRP().updateMultiplayerCustomServer(ip);
    }

}
