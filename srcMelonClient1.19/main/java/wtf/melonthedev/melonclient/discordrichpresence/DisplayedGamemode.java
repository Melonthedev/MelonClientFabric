package wtf.melonthedev.melonclient.discordrichpresence;

import wtf.melonthedev.melonclient.Client;

import java.util.ArrayList;
import java.util.List;

public enum DisplayedGamemode {
    BEDWARS("Bedwars", "bedwars"),
    SKYWARS("Skywars", "skywars"),
    SKYBLOCK("Skyblock", "skyblock"),
    DUELS("Duels", "duels"),
    UHC("UHC", "uhc");

    private final String name;
    private final String imageKey;

    DisplayedGamemode(String name, String imageKey) {
        this.name = name;
        this.imageKey = imageKey;
    }

    public String getName() {
        return name;
    }

    public String getImageKey() {
        return imageKey;
    }

    public static void displayGame(Minigame dg) {
        if (dg.getType() == null || dg.getTeamMembers() == null || dg.getServerIp() == null) throw new NullPointerException("DisplayedGamemodevalues are null!");
        Client.getInstance().getDiscordRP().updateMiniGame(dg);
    }
    public static String getTeamMemberString(List<String> teamMembers) {
        StringBuilder sb = new StringBuilder();
        for (String s : teamMembers) {
            sb.append(s).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

}
