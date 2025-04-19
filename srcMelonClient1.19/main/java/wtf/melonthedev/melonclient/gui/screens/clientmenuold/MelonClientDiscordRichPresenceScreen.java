package wtf.melonthedev.melonclient.gui.screens.clientmenuold;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientDiscordRichPresenceScreen extends MelonScreen {

    private final Screen parent;
    public String title;

    public MelonClientDiscordRichPresenceScreen(Screen parent) {
        super(Component.empty());
        this.parent = parent;
    }


}
