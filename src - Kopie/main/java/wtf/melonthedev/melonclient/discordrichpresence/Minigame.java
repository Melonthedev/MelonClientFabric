package wtf.melonthedev.melonclient.discordrichpresence;

import java.util.ArrayList;
import java.util.List;

public class Minigame {

    private final DisplayedGamemode gamemode;
    private final List<String> teamMembers = new ArrayList<>();
    private String type;
    private String serverIp;

    Minigame(DisplayedGamemode gamemode, String type, String serverIp, List<String> teamMembers) {
        if (teamMembers == null) teamMembers = new ArrayList<>();
        if (gamemode.getImageKey() == null || gamemode.getName() == null) throw new NullPointerException("DisplayedGamemodevalues are null!");

        this.gamemode = gamemode;
        this.type = type;
        this.serverIp = serverIp;
        this.teamMembers.addAll(teamMembers);
    }

    public DisplayedGamemode getGamemode() {
        return gamemode;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public String getType() {
        return type;
    }

    public String getServerIp() {
        return serverIp;
    }
}
