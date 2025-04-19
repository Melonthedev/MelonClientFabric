package wtf.melonthedev.melonclient.melonclientwrapper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.utils.RenderUtil;

public class MelonClientWrapper {

    private MelonClientWrapper() {}

    public static void setScreen(Screen screen) {
        Client.getMinecraft().setScreen(screen);
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static void openUrl(String url) {
        Util.getPlatform().openUri(url);
    }

    public static StringBuilder getMultiplayerTitle(StringBuilder sb) {
        if (Client.getMinecraft().getCurrentServer() != null) {
            sb.append(Client.getMinecraft().getCurrentServer().ip);
        } else {
            sb.append(I18n.get("title.multiplayer.other"));
        }
        return sb;
    }

    public static int getFps() {
        return getMinecraft().getFps();
    }

    public static void renderMainScreenLogo(GuiGraphics guiGraphics, float alphaFade) {
        int halfScreenWidth = (int)((double) getMinecraft().getWindow().getGuiScaledWidth() * 0.5D);
        int x = halfScreenWidth - 100;
        int y = 61;
        int width = 200;
        int height = 50;
        RenderUtil.drawMelonClientLogo(guiGraphics, x, y, width, height, alphaFade);
    }

    public static void renderDummyBossHealthAtPosition(GuiGraphics guiGraphics, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(ResourceLocation.parse("textures/gui/sprites/boss_bar/pink_progress.png"), x + 2, y + 11, 0, 0, 182, 5, 182, 5);
        Component component = Component.literal("Bossbar");
        int length = Client.getMinecraft().font.width(component);
        int xStr = x + 91 + 2 - length / 2;
        guiGraphics.drawStringWithBackdrop(Client.getMinecraft().font, component, xStr, y + 2,1, 16777215);
    }

}
