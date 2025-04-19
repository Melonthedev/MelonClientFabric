package wtf.melonthedev.melonclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
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
        this.addRenderableWidget(new Button(this.width - this.width / 8 - 40, this.height / 4 + 132, 80, 20, Component.literal("Done"), (p_96321_) -> Client.setScreen(parent)));

        this.addRenderableWidget(new Button(width / 2 - 100, height / 4 + 50, 200, 20, Component.literal("Select Cape"), (p_96321_) -> Client.setScreen(new SelectCapeScreen())));
        this.addRenderableWidget(new Button(width / 2 - 100, height / 4 + 75, 200, 20, Component.literal("Select Skin"), (p_96321_) -> System.out.println("Not implemented")));
        this.addRenderableWidget(new Button(width / 2 - 100, height / 4 + 100, 200, 20, Component.literal("Select Cosmetics"), (p_96321_) -> System.out.println("Not implemented")));
        //if (Client.entityRendererContext != null)
            //this.playerRenderer = new PlayerRenderer(Client.entityRendererContext, false);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 16777215);
  
        drawPlayer(stack);
    }

    int rotate;
    public void drawPlayer(PoseStack stack) {
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
