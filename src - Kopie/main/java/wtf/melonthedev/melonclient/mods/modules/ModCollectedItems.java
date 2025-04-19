package wtf.melonthedev.melonclient.modengine.mods;

import com.mojang.blaze3d.vertex.PoseStack;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

public class ModCollectedItems extends ModuleDraggable {

    public ModCollectedItems() {
        title = "Collected Items: ";
        name = "Collecteditems";
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
        super.renderDummy(pos, stack);
    }
}
