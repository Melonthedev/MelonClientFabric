package wtf.melonthedev.melonclient.modengine.mods;

import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;

public class ModBedwarsResourceCounter extends ModDraggable {

    public ModBedwarsResourceCounter() {
        super("Resource Counter", "resourcecounter");
        title = "Resources: ";
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
        guiGraphics.drawCenteredString(mc.font, "Gold: 5\nIron: 40\nEmerald: 4\nDiamond: 5", pos.getAbsoluteX() + 1, pos.getAbsoluteY(), getOptions().color.getColor() != null ? getOptions().color.getColor() : 0xFF00FFFf);
    }
}
