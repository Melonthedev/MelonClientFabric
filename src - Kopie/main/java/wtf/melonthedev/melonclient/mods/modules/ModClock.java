package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModFlag;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

import java.time.LocalDateTime;

public class ModClock extends ModuleDraggable {

    public ModClock() {
        title = "Time: ";
        name = "Clock";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        if (getOptions().flags.contains(ModFlag.USE_TWENTY_FOUR_HOUR_FORMAT))
            dummy = (getOptions().showModName ? title : "") + "13:16" + (getOptions().flags.contains(ModFlag.SHOW_SECONDS) ? ":00" : "");
        else dummy = (getOptions().showModName ? title : "") + "10:16" + (getOptions().flags.contains(ModFlag.SHOW_SECONDS) ? ":00" : "") + " AM";
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        LocalDateTime date = LocalDateTime.now();
        String hour = date.getHour() < 10 ? "0" + date.getHour() : String.valueOf(date.getHour());
        String minute = date.getMinute() < 10 ? "0" + date.getMinute() : String.valueOf(date.getMinute());
        String second = date.getSecond() < 10 ? "0" + date.getSecond() : String.valueOf(date.getSecond());

        if (getOptions().flags.contains(ModFlag.USE_TWENTY_FOUR_HOUR_FORMAT))
            text = (getOptions().showModName ? title : "") + hour + ":" + minute + (getOptions().flags.contains(ModFlag.SHOW_SECONDS) ? ":" + second : "");
        else
            text = (getOptions().showModName ? title : "") + (date.getHour() > 12 ? (date.getHour() - 12 < 10 ? "0" + (date.getHour() - 12) : date.getHour() - 12 ) : hour) + ":" + minute + (getOptions().flags.contains(ModFlag.SHOW_SECONDS) ? ":" + second : "") + (date.getHour() > 12 ? " PM" : " AM");
        renderBackground(stack, pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(stack, pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }
}
