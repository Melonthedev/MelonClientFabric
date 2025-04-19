package wtf.melonthedev.melonclient.gui.modengine;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class BackgroundCustomizerScreen extends MelonScreen {

    public BackgroundCustomizerScreen() {
        super(Component.literal("Edit Background Color"), true);
    }

    @Override
    public void init() {
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), (button) ->
                Client.setScreen(parent)
        ).bounds(this.width / 2 - 102, this.height / 6 + 160, 204, 20).build());
    }
}
