package wtf.melonthedev.melonclient.modengine.mods;

import com.mojang.blaze3d.vertex.PoseStack;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

public class ModHelloWorld extends ModuleDraggable {

    public ModHelloWorld() {
        title = "Hello World: ";
        name = "Helloworld";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? title : "") + "Hello World";
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        text = (getOptions().showModName ? title : "") + "Hello World";
        //fill(stack, mc.getWindow().getGuiScaledWidth() - this.font.width(text) - 3, 10, mc.getWindow().getGuiScaledWidth() - 1, 19, -1873784752);
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        //ScaledResolution resolution = new ScaledResolution(mc);
        //updateAbs(resolution);
        //pos.updateRelPos();
        //pos.updateAbsPos();
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
        //font.draw(stack, pos.getAbsoluteX() + ", " + pos.getAbsoluteY() + ", " + resolution.getScaledWidth(), pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFf);
        //font.draw(stack, "RX: " + pos.getRelativeX() + ", AX: " + pos.getAbsoluteX() + ", SW: " + resolution.getScaledWidth(), pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFf);
    }


}
