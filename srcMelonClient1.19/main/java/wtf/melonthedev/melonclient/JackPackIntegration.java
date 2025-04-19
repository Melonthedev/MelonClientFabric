package wtf.melonthedev.melonclient;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.Pack;

public class JackPackIntegration {

    public static final Minecraft mc = Client.getMinecraft();

    public static void onReload() {
        if (Client.getMinecraft().getResourcePackRepository().getSelectedPacks().size() <= 0) return;
        Pack[] packs = Client.getMinecraft().getResourcePackRepository().getSelectedPacks().toArray(new Pack[0]);
        Pack entry = packs[packs.length - 1];
        //Client.inventoryTextColor = entry != null ? entry.getInventoryTextColor() : 4210752;
    }

    public static void handleJackPackOnClientStart() {
        //if (mc.options.melonEnableJackPack)
        //    enableJackPack();
    }

    public static boolean isJackPackEnabled() {
        return false;// mc.options.melonEnableJackPack && mc.getResourcePackRepository().getResourcePackInstance() != null;
    }

    public static void enableJackPack() {
        //mc.options.melonEnableJackPack = true;
        //mc.gameSettings.saveOptions();
        //mc.getResourcePackRepository().downloadResourcePack("https://melonclient.melonthedev.wtf/downloads/JackPack/JackPack-1.8.zip", "");
    }

    public static void disableJackPack() {
        //mc.gameSettings.melonEnableJackPack = false;
        //mc.gameSettings.saveOptions();
        //mc.getResourcePackRepository().clearResourcePack();
    }


}
