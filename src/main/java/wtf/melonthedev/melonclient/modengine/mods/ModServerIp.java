package wtf.melonthedev.melonclient.modengine.mods;

import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

public class ModServerIp extends ModuleDraggable {

    public ModServerIp() {
        title = "Server IP: ";
        name = "Serverip";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? title : "") + "hypixel.net";
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (this.mc.getCurrentServer() == null) return;
        text = (getOptions().showModName ? title : "") + this.mc.getCurrentServer().ip;
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }
}
