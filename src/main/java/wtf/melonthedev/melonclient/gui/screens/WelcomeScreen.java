package wtf.melonthedev.melonclient.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class WelcomeScreen extends MelonScreen {

    public WelcomeScreen() {
        super(Component.literal("Welcome to MelonClient"), false);
        shouldDrawTitleString(false);
    }

    @Override
    public void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.literal("Get Started"), (p_96321_) -> MelonClientWrapper.openUrl(Client.gettingStartedUrl)).bounds(width/2 - 100, height/2 + 40, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Close"), (p_96321_) -> {
            Client.showWelcomeScreen = false;
            Client.setScreen(new TitleScreen());
        }).bounds(width/2 - 100, height/2 + 65, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        drawMelonClientLogoHeader(guiGraphics, width/2, 5);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(3, 3, 3);
        guiGraphics.drawCenteredString(mc.font, title, (width / 2) / 3, (height / 2 - 30) / 3, 0xffffff);
        guiGraphics.pose().popPose();
        guiGraphics.drawCenteredString(mc.font, "To get started with MelonClient click the button below.", width / 2, height / 2 + 10, 0xffffff);
    }
}
