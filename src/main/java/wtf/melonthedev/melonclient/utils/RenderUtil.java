package wtf.melonthedev.melonclient.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;

public class RenderUtil {

    public static void drawMelonClientLogo(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        guiGraphics.blit(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, x, y, 0.0F, 0.0F, 256, 44, 256, 64); // WIDTH???
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }


}
