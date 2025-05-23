package wtf.melonthedev.melonclient.modengine.mods;

import com.mojang.blaze3d.vertex.PoseStack;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

public class ModBedwarsResourceCounter extends ModuleDraggable {

    public ModBedwarsResourceCounter() {
        title = "Resources: ";
        name = "resourcecounter";
        updateDummy();
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {

    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        mc.font.draw(stack, "Gold: 5\nIron: 40\nEmerald: 4\nDiamond: 5", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFf);
    }
}
