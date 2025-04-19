package wtf.melonthedev.melonclient.modengine.mods;

import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModFlag;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

import java.util.ArrayList;
import java.util.List;

public class ModCps extends ModuleDraggable {

    private final List<Long> clicksRight = new ArrayList<>();
    private final List<Long> clicksLeft = new ArrayList<>();
    private boolean wasPressedRight;
    private boolean wasPressedLeft;
    private long lastPressedRight;
    private long lastPressedLeft;

    public ModCps() {
        title = "CPS: ";
        name = "Cps";
        updateDummy();
    }



    @Override
    public void updateDummy() {
        boolean showRight = getOptions().flags.contains(ModFlag.SHOW_RIGHT_CPS);
        boolean showLeft = getOptions().flags.contains(ModFlag.SHOW_LEFT_CPS);
        dummy = (getOptions().showModName ? title : "") + (showLeft && showRight ? "12 | 0" : (showLeft ? "0" : (showRight ? "12" : "")));
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        final boolean pressedRight = mc.mouseHandler.isRightPressed();
        final boolean pressedLeft = mc.mouseHandler.isLeftPressed();
        if (pressedRight != this.wasPressedRight) {
            this.lastPressedRight = System.currentTimeMillis();
            this.wasPressedRight = pressedRight;
            if (pressedRight) this.clicksRight.add(this.lastPressedRight);
        }
        if (pressedLeft != this.wasPressedLeft) {
            this.lastPressedLeft = System.currentTimeMillis();
            this.wasPressedLeft = pressedLeft;
            if (pressedLeft) this.clicksLeft.add(this.lastPressedLeft);
        }
        boolean showRight = getOptions().flags.contains(ModFlag.SHOW_RIGHT_CPS);
        boolean showLeft = getOptions().flags.contains(ModFlag.SHOW_LEFT_CPS);
        text = (getOptions().showModName ? "CPS: " : "") + (showLeft && showRight ? getCpsLeft() + " | " + getCpsRight() : (showLeft ? getCpsLeft() : (showRight ? getCpsRight() : "")));
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }

    private int getCpsRight() {
        final long time = System.currentTimeMillis();
        this.clicksRight.removeIf(aLong -> aLong + 1000 < time);
        return this.clicksRight.size();
    }
    private int getCpsLeft() {
        final long time = System.currentTimeMillis();
        this.clicksLeft.removeIf(aLong -> aLong + 1000 < time);
        return this.clicksLeft.size();
    }

}
