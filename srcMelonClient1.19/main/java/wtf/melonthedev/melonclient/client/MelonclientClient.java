package wtf.melonthedev.melonclient.client;

import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.language.I18n;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.utils.ClientSettings;

@Environment(EnvType.CLIENT)
public class MelonclientClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        for (KeyMapping melonClientKeyMapping : ClientSettings.getMelonClientKeyMappings()) {
            KeyBindingHelper.registerKeyBinding(melonClientKeyMapping);
        }
    }

}
