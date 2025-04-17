package wtf.melonthedev.melonclient.gui.screens.clientmenu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientProfileScreen extends MelonScreen {

    public MelonClientProfileScreen(Screen parent) {
        super(Component.literal("Your MelonClient Profile"), parent,true);
        shouldDrawTitleString(true, true);
        shouldDrawDoneButton(true);
    }

    @Override
    public void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.literal("Friends"), button -> Util.getPlatform().openUri(Client.friendsUrl)).bounds(this.width / 2 - 100, this.height / 4 + 50, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Your Cosmetics"), button -> Util.getPlatform().openUri(Client.friendsUrl)).bounds(this.width / 2 - 100, this.height / 4 + 75, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("..."), button -> Util.getPlatform().openUri(Client.friendsUrl)).bounds(this.width / 2 - 100, this.height / 4 + 100, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        drawMelonClientLogoHeader(guiGraphics, width / 2, 5);
    }
}
