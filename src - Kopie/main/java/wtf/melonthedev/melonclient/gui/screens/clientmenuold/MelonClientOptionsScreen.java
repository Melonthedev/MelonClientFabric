package wtf.melonthedev.melonclient.gui.screens.clientmenuold;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.modhud.HudManager;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientOptionsScreen extends MelonScreen {

    public MelonClientOptionsScreen(Screen parent) {
        super(Component.literal(Client.getInstance().NAME + " Options"), true);
        this.parent = parent;
    }

    @Override
    public void init()
    {
        addRenderableWidget(new Button(this.width - 65, 5, 60, 20, Component.literal("HUD Editor"), (button) ->
                HudManager.getINSTANCE().openConfigScreen()));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6, 204, 20, Component.literal("Global Settings"), (button) ->
                mc.setScreen(new MelonClientGlobalSettingsScreen(this))));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 25, 204, 20, Component.literal("Features"), (button) ->
                mc.setScreen(new MelonClientModsScreen(this))));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 50, 204, 20, Component.literal("Cosmetics"), (button) ->
                mc.setScreen(new MelonClientModsScreen(this))));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 75, 204, 20, Component.literal("Discord Server"), (button) ->
                mc.setScreen(new ConfirmLinkScreen(t -> {
                    if (t) Util.getPlatform().openUri("https://discord.gg/CQQm6sS4Z8");
                    else mc.setScreen(this);
                }, "https://discord.gg/CQQm6sS4Z8", true))));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 100, 204, 20, Component.literal("Website"), (button) ->
                mc.setScreen(new ConfirmLinkScreen(t -> {
                        if (t) Util.getPlatform().openUri("https://melonclient.melonthedev.wtf/");
                        else mc.setScreen(this);
                }, "https://melonclient.melonthedev.wtf/", true))));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 125, 204, 20, Component.literal("More"), (button) ->
                mc.setScreen(new MelonClientAdvancedSettingsScreen( this))));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 160, 204, 20, Component.translatable("gui.done"), (button) ->
                mc.setScreen(parent)));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawCenteredString(stack, font, title, width / 2, 20, 0xFFFFFF);
  
    }
}
