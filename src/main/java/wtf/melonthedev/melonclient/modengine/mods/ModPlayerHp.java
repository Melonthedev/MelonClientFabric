package wtf.melonthedev.melonclient.modengine.mods;

import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;

public class ModPlayerHp extends ModDraggable {

    public ModPlayerHp() {
        super("Player Health", "playerhp");
        title = "Player HP: ";
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
        if (this.mc.getCurrentServer() == null) return;
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        super.renderDummy(pos, guiGraphics);
    }
}
