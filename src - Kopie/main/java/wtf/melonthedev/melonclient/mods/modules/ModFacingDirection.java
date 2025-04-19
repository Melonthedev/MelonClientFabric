package wtf.melonthedev.melonclient.modengine.mods;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.modengine.ModFlag;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

public class ModFacingDirection extends ModuleDraggable {

    public ModFacingDirection() {
        title = "Facing Direction: ";
        name = "Facingdirection";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        if (getOptions().flags.contains(ModFlag.SHOW_LONGER_MOD_NAME))
            title = "Facing Direction: ";
        else
            title = "Facing: ";
        dummy = (getOptions().showModName ? title : "") + "North";
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (Client.getMinecraft().player == null) return;
        String str = Client.getMinecraft().player.getDirection().getName();
        text = (getOptions().showModName ? title : "") + str.substring(0, 1).toUpperCase() + str.substring(1);
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }
}
