package wtf.melonthedev.melonclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.GuiUtils;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class SkinCapeChangeScreen extends MelonScreen {

    private final Screen parent;

    public SkinCapeChangeScreen(Screen parent) {
        super(Component.literal("Skin and Cape"), true);
        this.parent = parent;
    }

    @Override
    public void init()
    {
        this.addRenderableWidget(Button.builder(Component.literal("Done"), (p_96321_) -> Client.setScreen(parent)).bounds(this.width - this.width / 8 - 40, this.height / 4 + 132, 80, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Select Cape"), (p_96321_) -> Client.setScreen(new SelectCapeScreen())).bounds(width / 2 - 100, height / 4 + 50, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Select Skin"), (p_96321_) -> System.out.println("Not implemented")).bounds(width / 2 - 100, height / 4 + 75, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Select Cosmetics"), (p_96321_) -> System.out.println("Not implemented")).bounds(width / 2 - 100, height / 4 + 100, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
  
        drawPlayer(guiGraphics);
    }

    public void drawPlayer(GuiGraphics guiGraphics) {
        GuiUtils.renderRotatingPlayerInInventory(guiGraphics, this.width - this.width / 8, this.height / 4 + 65, 50);
    }

    @Override
    public void onClose() {
        super.onClose();
        //localPlayer.remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
    }
}
