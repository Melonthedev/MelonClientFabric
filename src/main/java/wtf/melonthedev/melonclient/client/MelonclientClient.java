package wtf.melonthedev.melonclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import wtf.melonthedev.melonclient.settings.ClientSettings;

@Environment(EnvType.CLIENT)
public class MelonclientClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        for (KeyMapping melonClientKeyMapping : ClientSettings.getMelonClientKeyMappings()) {
            KeyBindingHelper.registerKeyBinding(melonClientKeyMapping);
        }
    }

}
