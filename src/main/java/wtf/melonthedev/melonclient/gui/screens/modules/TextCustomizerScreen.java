package wtf.melonthedev.melonclient.gui.screens.modules;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class TextCustomizerScreen extends MelonScreen {

    public TextCustomizerScreen() {
        super(Component.literal("Edit Mod Text"), true);
    }

    @Override
    public void init() {
        addRenderableWidget(Button.builder(Component.literal("Color: White"), (button) ->
                Client.setScreen(parent)
        ).bounds(this.width / 2 - 102, this.height / 6 + 160, 204, 20).build());



        addRenderableWidget(Button.builder(Component.translatable("gui.done"), (button) ->
                Client.setScreen(parent)
        ).bounds(this.width / 2 - 102, this.height / 6 + 160, 204, 20).build());
    }
}
