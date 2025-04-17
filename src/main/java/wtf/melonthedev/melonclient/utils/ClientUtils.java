package wtf.melonthedev.melonclient.utils;

import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.mods.ModFlag;
import wtf.melonthedev.melonclient.mods.ModInstanceManager;

public class ClientUtils {

    public static void setModDefaults() {
        ModInstanceManager.modArmorstatus.getOptions().init();
        ModInstanceManager.modClock.getOptions().init();
        ModInstanceManager.modCps.getOptions().init();
        ModInstanceManager.modDate.getOptions().init();
        ModInstanceManager.modFacingDirection.getOptions().init();
        ModInstanceManager.modFps.getOptions().init();
        ModInstanceManager.modPing.getOptions().init();
        ModInstanceManager.modPotionStatus.getOptions().init();
        ModInstanceManager.modServerIp.getOptions().init();
        ModInstanceManager.modCoordPosition.getOptions().init();

        ModInstanceManager.modArmorstatus.getOptions().position = DefaultValues.defaultPosArmorStatus;
        ModInstanceManager.modClock.getOptions().position = DefaultValues.defaultPosClock;
        ModInstanceManager.modCps.getOptions().position = DefaultValues.defaultPosCps;
        ModInstanceManager.modDate.getOptions().position = DefaultValues.defaultPosDate;
        ModInstanceManager.modFacingDirection.getOptions().position = DefaultValues.defaultPosFacingDirection;
        ModInstanceManager.modFps.getOptions().position = DefaultValues.defaultPosFps;
        ModInstanceManager.modPing.getOptions().position = DefaultValues.defaultPosPing;
        ModInstanceManager.modPotionStatus.getOptions().position = DefaultValues.defaultPosPotionStatus;
        ModInstanceManager.modServerIp.getOptions().position = DefaultValues.defaultPosServer;
        ModInstanceManager.modCoordPosition.getOptions().position = DefaultValues.defaultPosCoords;


        ModInstanceManager.modCps.getOptions().flags.add(ModFlag.SHOW_LEFT_CPS);
        ModInstanceManager.modCps.getOptions().flags.add(ModFlag.SHOW_RIGHT_CPS);
    }

    public static boolean hasUsedClientBefore() {
        return FileManager.getRootDir().exists();
    }

    public static void logDev(String message) {
        if (!Client.isDevModeEnabled()) return;
        System.out.println("[" + Client.getInstance().NAME + "] (DevMode) " + message);
    }
    public static void logDev(Component message) {
        logDev(message.getString());
    }


}
