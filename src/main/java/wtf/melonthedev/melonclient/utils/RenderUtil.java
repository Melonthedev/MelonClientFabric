package wtf.melonthedev.melonclient.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;

public class RenderUtil {

    public static void drawMelonClientLogo(GuiGraphics guiGraphics, int x, int y) {
        drawMelonClientLogo(guiGraphics, x, y, 200, 50);
    }

    public static void drawMelonClientLogo(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        drawMelonClientLogo(guiGraphics, x, y, width, height, 1);
    }

    public static void drawMelonClientLogo(GuiGraphics guiGraphics, int x, int y, int width, int height, float alpha) {
        //ClientUtils.logDev("Drawing MelonClient Logo");
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.enableBlend();
        guiGraphics.blit(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, x, y, 0, 0, width/2, height, width/2, height * 2);
        guiGraphics.blit(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, x + width/2, y, 0, height, width/2, height, width/2, height * 2);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }


}
