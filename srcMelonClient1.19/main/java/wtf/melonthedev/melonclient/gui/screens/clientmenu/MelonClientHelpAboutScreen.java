package wtf.melonthedev.melonclient.gui.screens.clientmenu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientHelpAboutScreen extends MelonScreen {

    public MelonClientHelpAboutScreen(Screen parent) {
        super(Component.literal("Help & About"), parent, true);
        shouldDrawTitleString(true, true);
        shouldDrawDoneButton(true);
    }

    @Override
    public void init()
    {
        super.init();
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 50, 200, 20, Component.literal("Getting Started"), button -> Util.getPlatform().openUri(Client.gettingStartedUrl)));
        this.addRenderableWidget(new Button(this.width / 2 + 2, this.height / 4 + 75, 98, 20, Component.literal("Website"), button -> Client.setScreen(new ConfirmLinkScreen(result -> {
            if (result) Util.getPlatform().openUri(Client.websiteUrl);
            Client.setScreen(new MelonClientHelpAboutScreen(parent));
        }, Client.websiteUrl, true))));
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 75, 98, 20,  Component.literal("Discord"),  button -> Client.setScreen(new ConfirmLinkScreen(result -> {
            if (result) Util.getPlatform().openUri(Client.discordUrl);
            Client.setScreen(new MelonClientHelpAboutScreen(parent));
        }, Client.discordUrl, true))));
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 100, 200, 20,  Component.literal("Developer Options"), button -> Client.setScreen(new MelonClientDeveloperOptionsScreen(this))));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawMelonClientLogoHeader(stack, width / 2, 5);
    }
}
