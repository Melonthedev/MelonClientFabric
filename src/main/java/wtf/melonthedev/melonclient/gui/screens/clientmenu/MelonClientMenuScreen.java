package wtf.melonthedev.melonclient.gui.screens.clientmenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.modengine.rendering.HudManager;
import wtf.melonthedev.melonclient.gui.screens.cape.SkinCapeChangeScreen;
import wtf.melonthedev.melonclient.gui.screens.clientmenu.options.MelonClientOptionsScreen;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientMenuScreen extends MelonScreen {

    public MelonClientMenuScreen(Screen parent) {
        super(Component.literal("Menu"), parent, true);
        shouldDrawTitleString(true, true);
        shouldDrawDoneButton(true);
        shouldSyncPlayerRotation(true);
    }

    @Override
    public void init() {
        super.init();
        loadPlayer();
        this.addRenderableWidget(Button.builder(Component.literal("HUD Editor"), button -> HudManager.getINSTANCE().openConfigScreen()).bounds(this.width - 65, 5, 60, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Skin & Cape"), button -> Client.setScreen(new SkinCapeChangeScreen(this))).bounds(this.width - this.width / 8 - 40, this.height / 4 + 132, 80, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Options"), button -> Client.setScreen(new MelonClientOptionsScreen(this))).bounds(this.width / 2 - 100, this.height / 4 + 50, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("My Profile"), button -> Client.setScreen(new MelonClientProfileScreen(this))).bounds(this.width / 2 - 100, this.height / 4 + 75, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Help & About") , button -> Client.setScreen(new MelonClientHelpAboutScreen(this))).bounds(this.width / 2 - 100, this.height / 4 + 100, 200, 20).build());
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        drawMelonClientLogoHeader(guiGraphics, width / 2, 5);
        drawPlayerWithName(guiGraphics, this.width - this.width / 8, this.height / 4 + 130 - 8, 60, partialTicks);
    }
}
