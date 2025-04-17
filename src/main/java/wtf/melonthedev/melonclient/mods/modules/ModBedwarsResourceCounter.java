package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

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
        guiGraphics.drawCenteredString(mc.font, "Gold: 5\nIron: 40\nEmerald: 4\nDiamond: 5", pos.getAbsoluteX() + 1, pos.getAbsoluteY(), options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFf);
    }
}
