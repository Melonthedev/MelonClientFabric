package wtf.melonthedev.melonclient.modengine.mods.vanilla;

import com.mojang.blaze3d.vertex.PoseStack;
import wtf.melonthedev.melonclient.modengine.rendering.HudManager;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

public class ModBossbar extends ModuleDraggable {
    public ModBossbar() {
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
        MelonClientWrapper.renderDummyBossHealthAtPosition(stack, HudManager.getINSTANCE().getConfigScreen(), pos.getAbsoluteX(), pos.getAbsoluteY());
    }
}
