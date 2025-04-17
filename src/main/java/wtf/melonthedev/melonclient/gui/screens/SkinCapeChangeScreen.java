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
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class SkinCapeChangeScreen extends MelonScreen {

    private final Screen parent;
    //private LocalPlayer localPlayer;
    //private PlayerRenderer playerRenderer;

    public SkinCapeChangeScreen(Screen parent) {
        super(Component.literal("Skin and Cape"), true);
        this.parent = parent;
    }

    @Override
    public void init()
    {
        //
        // localPlayer = new LocalPlayer(minecraft, minecraft.level, minecraft.player.connection, minecraft.player.getStats(), minecraft.player.getRecipeBook(), false, false);
        this.addRenderableWidget(Button.builder(Component.literal("Done"), (p_96321_) -> Client.setScreen(parent)).bounds(this.width - this.width / 8 - 40, this.height / 4 + 132, 80, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Select Cape"), (p_96321_) -> Client.setScreen(new SelectCapeScreen())).bounds(width / 2 - 100, height / 4 + 50, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Select Skin"), (p_96321_) -> System.out.println("Not implemented")).bounds(width / 2 - 100, height / 4 + 75, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Select Cosmetics"), (p_96321_) -> System.out.println("Not implemented")).bounds(width / 2 - 100, height / 4 + 100, 200, 20).build());
        //if (Client.entityRendererContext != null)
            //this.playerRenderer = new PlayerRenderer(Client.entityRendererContext, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
  
        drawPlayer(guiGraphics);
    }

    int rotate;
    public void drawPlayer(GuiGraphics guiGraphics) {
        //ScreenDebug.drawEntityOnScreen(this.width - this.width / 8, this.height / 4 + 130, 60, rotate, 180, player);
        //if (localPlayer == null) return;
        //localPlayer.getInventory().clearContent();
        //MelonClientWrapper.renderEntityInInventory(width - width/8, height/3 + 100, 60, rotate, 1.0f, localPlayer, true);
        //playerRenderer.setModelProperties(localPlayer);
        //playerRenderer.render(localPlayer, 1.0F, 1.0F, stack, MultiBufferSource.immediate(new BufferBuilder(0)), 1);
        rotate+=2;
        if(rotate<= -720 || rotate >= 720) {
            rotate = 0;
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        //localPlayer.remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
    }
}
