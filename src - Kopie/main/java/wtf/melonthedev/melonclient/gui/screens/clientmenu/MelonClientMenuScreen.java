package wtf.melonthedev.melonclient.gui.screens.clientmenu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.modhud.HudManager;
import wtf.melonthedev.melonclient.gui.screens.SkinCapeChangeScreen;
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
        this.addRenderableWidget(new Button(this.width - 65, 5, 60, 20,  Component.literal("HUD Editor"), button -> HudManager.getINSTANCE().openConfigScreen()));
        this.addRenderableWidget(new Button(this.width - this.width / 8 - 40, this.height / 4 + 132, 80, 20, Component.literal("Skin & Cape"), button -> Client.setScreen(new SkinCapeChangeScreen(this))));
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 50, 200, 20, Component.literal("Options"), button -> Client.setScreen(new MelonClientOptionsScreen(this))));
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 75, 200, 20, Component.literal("My Profile"), button -> Client.setScreen(new MelonClientProfileScreen(this))));
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 100, 200, 20, Component.literal("Help & About") , button -> Client.setScreen(new MelonClientHelpAboutScreen(this))));
    }

    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawMelonClientLogoHeader(stack, width / 2, 5);
        drawPlayerWithName(stack, this.width - this.width / 8, this.height / 4 + 130 - 8, 60, partialTicks);
    }
}
