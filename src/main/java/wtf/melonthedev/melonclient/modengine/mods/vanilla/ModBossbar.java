package wtf.melonthedev.melonclient.modengine.mods.vanilla;

import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;

public class ModBossbar extends ModDraggable {
    public ModBossbar() {
        super("Bossbar", "vanilla-bossbar");
        title = "";
        updateDummy();
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public int getWidth() {
        return 184;
    }

    @Override
    public void updateDummy() {
        dummy = "";
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {

    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        MelonClientWrapper.renderDummyBossHealthAtPosition(guiGraphics, pos.getAbsoluteX(), pos.getAbsoluteY());
    }
}
