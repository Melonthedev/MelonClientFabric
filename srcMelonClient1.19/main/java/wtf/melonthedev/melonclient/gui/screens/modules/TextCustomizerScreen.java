package wtf.melonthedev.melonclient.gui.modengine;

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
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 160, 204, 20, Component.literal("Color: White"), (button) ->
                Client.setScreen(parent)
        ));



        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 160, 204, 20, Component.translatable("gui.done"), (button) ->
                Client.setScreen(parent)
        ));
    }
}
