package wtf.melonthedev.melonclient.discordrichpresence;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.Objective;
import wtf.melonthedev.melonclient.Client;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class DiscordRP {

    private boolean running = true;
    private long created;
    private String type; // Currently played Minigame type (e.g. doubles,3v3v3v3)
    private boolean minigameDisplayed = false;

    public void init() {
        this.created = System.currentTimeMillis();
        this.minigameDisplayed = false;
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(user ->
                Client.getInstance().getLOGGER().debug("Discord RPC Ready. Connected User: " + user.username + "#" + user.discriminator)
        ).build();
        DiscordRPC.discordInitialize("894537276011462666", handlers, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        running = false;
        this.minigameDisplayed = false;
        DiscordRPC.discordShutdown();
    }

    public void handleMiniGame() {
        CompletableFuture.runAsync(() -> {
            if (Client.getMinecraft().getCurrentServer() == null
                    || !Client.getMinecraft().getCurrentServer().ip.contains("hypixel.net")
                    || Client.getMinecraft().level == null) return;
            for (Objective objective : Client.getMinecraft().level.getScoreboard().getObjectives()) {
                switch (objective.getName()) {
                    case "BForeboard": //Bedwars
                        updateMiniGame(new Minigame(DisplayedGamemode.BEDWARS, "Solo", "hypixel.net", new ArrayList<>()));
                        break;
                    case "SForeboard": //Skywars
                        updateMiniGame(new Minigame(DisplayedGamemode.SKYWARS, "Solo Normal", "hypixel.net", new ArrayList<>()));
                        break;
                    case "SBScoreboard": //Skyblock
                        updateMiniGame(new Minigame(DisplayedGamemode.SKYBLOCK, "on Hypixel", "hypixel.net", new ArrayList<>()));
                        break;
                    case "PreScoreboard": //Pre Game
                        System.out.println("PRE GAME");
                        for (Component component : objective.getDisplayName().getSiblings()) {
                            if (component.getString().contains("BED WARS")) {
                                updateMiniGame(new Minigame(DisplayedGamemode.BEDWARS, "Solo", "hypixel.net", new ArrayList<>()));
                                return;
                            } else if (component.getString().contains("SKY WARS")) {
                                updateMiniGame(new Minigame(DisplayedGamemode.BEDWARS, "Solo", "hypixel.net", new ArrayList<>()));
                                return;
                            }
                        }
                        System.out.println(objective.getDisplayName());
                        break;
                    default:
                        if (minigameDisplayed) DisplayedServer.displayServer(Client.getMinecraft().getCurrentServer().ip);
                        break;
                }
            }
        });

    }

    public void update(String first, String second) {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(second);
        builder.setStartTimestamps(created);
        builder.setDetails(first);
        builder.setBigImage("large", "Minecraft " + SharedConstants.getCurrentVersion().getName() + "\n " + Client.getInstance().NAMEVVERSION + "\n for Fabric by Melonthedev");
        DiscordRPC.discordUpdatePresence(builder.build());
    }

    public void update(String first, String second, String key, String hoverText) {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(second);
        builder.setStartTimestamps(created);
        builder.setDetails(first);
        builder.setBigImage("large", "Minecraft " + SharedConstants.getCurrentVersion().getName() + "\n " + Client.getInstance().NAMEVVERSION + "\n for Fabric by Melonthedev");
        builder.setSmallImage(key, hoverText);
        DiscordRPC.discordUpdatePresence(builder.build());
    }

    public void updateMiniGame(Minigame game) {
        resetTime();
        this.minigameDisplayed = true;
        update("Playing " + game.getGamemode().getName(), game.getTeamMembers().isEmpty() ? game.getType() : "With " + DisplayedGamemode.getTeamMemberString(game.getTeamMembers()), game.getGamemode().getImageKey(), game.getGamemode().getName() + " " + game.getType() + " on " + game.getServerIp());
    }

    public void updateSingleplayer(boolean resetTime) {
        if (resetTime) resetTime();
        if (Client.getMinecraft().hasSingleplayerServer()) Client.getInstance().getDiscordRP().update("Playing Singleplayer", "But why?", "singleplayer", "Singleplayer");
    }

    public void updateMainMenu() {
        resetTime();
        update("Idle", "Main Menu", "mainmenu", "Menu Screen");
    }
    public void updateDisconnectedScreen() {
        resetTime();
        update("In Menu", "Disconnected", "menu", "");
    }

    public void updateMultiplayerSelectionMenu() {
        resetTime();
        update("In Menu", "Multiplayer Selection", "menu", "");
    }

    public void updateMultiplayerServer(String fullIp, String imageKey) {
        resetTime();
        update("Playing Multiplayer", fullIp, imageKey, fullIp);
    }
    public void updateMultiplayerCustomServer(String ip) {
        resetTime();
        update("Playing Multiplayer", ip, "multiplayer", ip);
    }

    public void updateWorldSelection() {
        resetTime();
        update("In Menu", "World Selection", "menu", "");
    }

    public void resetTime() {
        created = System.currentTimeMillis();
        this.minigameDisplayed = false;
    }

}
