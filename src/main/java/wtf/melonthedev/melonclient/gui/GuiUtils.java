package wtf.melonthedev.melonclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.Client;

public class GuiUtils {

    private GuiUtils() {}
    private static final Minecraft mc = Client.getMinecraft();
    public static PoseStack stack = new PoseStack();

    public static void drawMelonString(GuiGraphics guiGraphics) {
        guiGraphics.drawString(mc.font, "MelonClient", guiGraphics.guiWidth() - mc.font.width("MelonClient") - 4, guiGraphics.guiHeight() - mc.font.lineHeight - 4, 16777215);
    }

    public static void drawHollowRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int color) {
        guiGraphics.hLine(x, x + width, y, color);
        guiGraphics.hLine(x, x + width, y + height, color);
        guiGraphics.vLine(x, y + height, y, color);
        guiGraphics.vLine(x + width, y, y + height, color);
    }


    public static void drawTexture(GuiGraphics guiGraphics, int posX, int posY, int width, int height, ResourceLocation texture) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);
        guiGraphics.blit(texture, posX, posY, width, height, 128, 64);
    }

    public static void drawTexture(GuiGraphics guiGraphics, int xPos, int yPos, int blitOffset, float textureX, float textureY, int imgSizeX, int imgSizeY, int scaleX, int scaleY, ResourceLocation texture) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);
        guiGraphics.blit(texture, xPos, yPos, blitOffset, (int)textureX, (int)textureY, imgSizeX, imgSizeY, scaleX, scaleY);
    }


}
