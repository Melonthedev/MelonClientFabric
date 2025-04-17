package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModFlag;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

public class ModCoordPosition extends ModuleDraggable {

    public ModCoordPosition() {
        title = "Coords: ";
        name = "Coords";
        updateDummy();
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? title : "") + (getOptions().flags.contains(ModFlag.COORDS_SLASH_DESIGN) ? "1403 / 37 / -3482" : "X: 1403 Y: 37 Z: -3482");
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (mc.player == null) return;
        int x = mc.player.blockPosition().getX();
        int y = mc.player.blockPosition().getY();
        int z = mc.player.blockPosition().getZ();
        text = (getOptions().showModName ? title : "") + (getOptions().flags.contains(ModFlag.COORDS_SLASH_DESIGN) ? x + " / " + y + " / " + z : "X: " + x + " Y: " + y + " Z: " + z);
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }
}
