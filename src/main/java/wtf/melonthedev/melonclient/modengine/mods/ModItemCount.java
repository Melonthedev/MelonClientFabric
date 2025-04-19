package wtf.melonthedev.melonclient.modengine.mods;

import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;

public class ModItemCount extends ModDraggable {

    public ModItemCount() {
        super("Itemcounter", "itemcount");
        title = "Item Count: ";
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
