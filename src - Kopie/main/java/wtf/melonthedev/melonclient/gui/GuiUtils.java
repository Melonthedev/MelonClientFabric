package wtf.melonthedev.melonclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.Client;

public class GuiUtils {

    private GuiUtils() {}
    private static final Minecraft mc = Client.getMinecraft();
    public static PoseStack stack = new PoseStack();

    public static void drawMelonString(Screen screen) {
        screen.drawString(stack, mc.font, "MelonClient", screen.width - mc.font.width("MelonClient") - 4, screen.height - mc.font.lineHeight - 4, 16777215);
    }

    public static void drawHollowRect(GuiComponent screen, PoseStack stack, int x, int y, int width, int height, int color) {
        /*screen.hLine(stack, x, x + width, y, color);
        screen.hLine(stack, x, x + width, y + height, color);
        screen.vLine(stack, x, y + height, y, color);
        screen.vLine(stack, x + width, y, y + height, color);*/
    }


    public static void drawTexture(PoseStack stack, int posX, int posY, int width, int height, ResourceLocation texture, Screen screen) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);
        RenderSystem.setShaderTexture(0, texture);
        screen.blit(stack, posX, posY, width, height, 128, 64);
    }

    public static void drawTexture(PoseStack stack, int xPos, int yPos, int blitOffset, float textureX, float textureY, int imgSizeX, int imgSizeY, int scaleX, int scaleY, ResourceLocation texture, Screen screen) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);
        RenderSystem.setShaderTexture(0, texture);
        screen.blit(stack, xPos, yPos, blitOffset, (int)textureX, (int)textureY, imgSizeX, imgSizeY, scaleX, scaleY);
    }


}
