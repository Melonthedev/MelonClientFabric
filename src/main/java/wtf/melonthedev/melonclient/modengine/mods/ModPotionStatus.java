package wtf.melonthedev.melonclient.modengine.mods;

import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

import java.util.ArrayList;
import java.util.List;

public class ModPotionStatus extends ModuleDraggable {

    int heightMultiplier = 1;

    public ModPotionStatus() {
        title = "Potions:";
        name = "Potstatus";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? title + " " : "") + "Slow Falling 3 - 20s";
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (mc.player == null) return;
        List<String> potions = new ArrayList<>();
        mc.player.getActiveEffects().forEach(effect -> potions.add(effect.getEffect().getRegisteredName() + " " + effect.getAmplifier() + " - " + Math.round(effect.getDuration() / 10) + "s"));
        text = (getOptions().showModName ? title + " " : "");
        heightMultiplier = 1;
        for (String s : potions) {
            renderSmallBackground(guiGraphics.pose(), s, pos.getAbsoluteX(), pos.getAbsoluteY() + mc.font.lineHeight * heightMultiplier + 2);
            drawStandardText(guiGraphics, s, pos.getAbsoluteX(), pos.getAbsoluteY() + mc.font.lineHeight * heightMultiplier + 2);
            heightMultiplier++;
        }
        if (!text.equals("")) {
            renderBackground(guiGraphics.pose(), pos, text);
            drawStandardText(guiGraphics, pos, text);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }
}
