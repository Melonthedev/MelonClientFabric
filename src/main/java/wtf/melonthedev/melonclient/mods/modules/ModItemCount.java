package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

public class ModItemCount extends ModuleDraggable {

    public ModItemCount() {
        title = "Item Count: ";
        name = "Itemcount";
        updateDummy();
    }

    @Override
    public void updateDummy() {
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
        super.renderDummy(pos, guiGraphics);
    }
}
