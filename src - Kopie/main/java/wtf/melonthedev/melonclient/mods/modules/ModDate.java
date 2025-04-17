package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModFlag;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class ModDate extends ModuleDraggable {

    public ModDate() {
        title = "Date: ";
        name = "Date";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? title : "") + (getOptions().flags.contains(ModFlag.SHOW_DAY_OF_WEEK) ? "Mon. 01.01.2022" : "01.01.2022");
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        LocalDateTime date = LocalDateTime.now();
        text = (getOptions().showModName ? title : "") + (getOptions().flags.contains(ModFlag.SHOW_DAY_OF_WEEK) ? date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ROOT) + ". " : "") + date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear();
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }

}
