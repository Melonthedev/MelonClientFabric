package wtf.melonthedev.melonclient.modengine.mods;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

public class ModBedwarsTeamColor extends ModuleDraggable {

    public ModBedwarsTeamColor() {
        title = "Team: ";
        name = "Bedwarsteam";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? title : "") + "Red";
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (this.mc.getCurrentServer() == null) return;
        if (!mc.getCurrentServer().ip.contains("hypixel.net")) return;
        text = ChatFormatting.RED + "" + ChatFormatting.BOLD + (getOptions().showModName ? title : "") + "Red";
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }
}
