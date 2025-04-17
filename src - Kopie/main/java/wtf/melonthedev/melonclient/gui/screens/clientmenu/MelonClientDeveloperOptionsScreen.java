package wtf.melonthedev.melonclient.gui.screens.clientmenu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientDeveloperOptionsScreen extends MelonScreen {

    public MelonClientDeveloperOptionsScreen(Screen parent) {
        super(Component.literal("Developer Options"), parent, true);
        shouldDrawTitleString(true, true);
        shouldDrawDoneButton(true);
    }

    @Override
    public void init() {
        super.init();

    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawMelonClientLogoHeader(stack, width / 2, 5);
    }
}
