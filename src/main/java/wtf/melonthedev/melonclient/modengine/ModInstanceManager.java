package wtf.melonthedev.melonclient.modengine;

import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;
import wtf.melonthedev.melonclient.modengine.rendering.HudManager;
import wtf.melonthedev.melonclient.modengine.mods.*;
import wtf.melonthedev.melonclient.modengine.mods.vanilla.ModBossbar;
import wtf.melonthedev.melonclient.utils.ClientUtils;

public class ModInstanceManager {

    public static ModHelloWorld modHelloWorld;
    public static ModArmorstatus modArmorstatus;
    public static ModBedwarsResourceCounter modBedwarsResourceCounter;
    public static ModBedwarsTeamColor modBedwarsTeamColor;
    public static ModClock modClock;
    public static ModCollectedItems modCollectedItems;
    public static ModCoordPosition modCoordPosition;
    public static ModCps modCps;
    public static ModDate modDate;
    public static ModItemCount modItemCount;
    public static ModKeyStrokes modKeyStrokes;
    public static ModPing modPing;
    public static ModPotionStatus modPotionStatus;
    public static ModServerIp modServerIp;
    public static ModPlayerHp modPlayerHp;
    public static ModFps modFps;
    public static ModFacingDirection modFacingDirection;
    public static ModPerspective modPerspective;
    public static ModBossbar modBossbar;

    public static void register(HudManager hudManager) {
        //modHelloWorld = new ModHelloWorld();
        modCps = new ModCps();
        modClock = new ModClock();
        modDate = new ModDate();
        modPing = new ModPing();
        modServerIp = new ModServerIp();
        //modBedwarsTeamColor = new ModBedwarsTeamColor();
        modCoordPosition = new ModCoordPosition();
        modFps = new ModFps();
        modArmorstatus = new ModArmorstatus();
        modPotionStatus = new ModPotionStatus();
        modFacingDirection = new ModFacingDirection();
        modPerspective = new ModPerspective();
        modBossbar = new ModBossbar();
        if (Client.isNewUser) {
            ClientUtils.setModDefaults();
        }
        //modPerspective = new ModPerspective();
        hudManager.register(modCps, modClock, modDate, modPing, modServerIp, modCoordPosition, modFps, modArmorstatus, modPotionStatus, modFacingDirection, modPerspective, modBossbar);
    }

    public static ModDraggable[] getMods() {
        return new ModDraggable[] {modPing, modClock, modCps, modDate, modServerIp, modCoordPosition, modFps, modArmorstatus, modPotionStatus, modFacingDirection, modPerspective, modBossbar};
    }
}
