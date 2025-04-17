package wtf.melonthedev.melonclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
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
        this.addRenderableWidget(new Button(width/2 - 100, height/2 + 40, 200, 20, Component.literal("Get Started"), (p_96321_) -> MelonClientWrapper.openUrl(Client.gettingStartedUrl)));
        this.addRenderableWidget(new Button(width/2 - 100, height/2 + 65, 200, 20, Component.literal("Close"), (p_96321_) -> {
            Client.showWelcomeScreen = false;
            Client.setScreen(new TitleScreen());
        }));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawMelonClientLogoHeader(stack, width/2, 5);
        stack.pushPose();
        stack.scale(3, 3, 3);
        super.drawCenteredString(stack, mc.font, title, (width / 2) / 3, (height / 2 - 30) / 3, 0xffffff);
        stack.popPose();
        drawCenteredString(stack, mc.font, "To get started with MelonClient click the button below.", width / 2, height / 2 + 10, 0xffffff);
    }
}
