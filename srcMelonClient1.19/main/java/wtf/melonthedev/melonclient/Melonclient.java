package wtf.melonthedev.melonclient;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;

public class Melonclient implements ModInitializer {
    @Override
    public void onInitialize() {
        Client.getInstance().initialize();
    }
}
