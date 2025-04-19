package wtf.melonthedev.melonclient.listeners;

import net.minecraft.client.Minecraft;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.event.EventTarget;
import wtf.melonthedev.melonclient.event.events.ClientTickEvent;
import wtf.melonthedev.melonclient.settings.ClientSettings;

public class TickListener {

    Minecraft mc = Client.getMinecraft();

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (ClientSettings.keyMelonHudEditor.isDown()) {
            Client.getInstance().getHudManager().openConfigScreen();
        }
        //TODO: Show emote seletion menu

    }

}
