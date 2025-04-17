package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.PlayerInfo;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

public class ModPing extends ModuleDraggable {

    public ModPing() {
        title = "Ping: ";
        name = "Ping";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? title : "") + "10ms";
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (this.mc.player == null) return;
        PlayerInfo playerinfo = this.mc.player.connection.getPlayerInfo(mc.player.getUUID());
        int ping = 0;
        if (playerinfo != null)
            ping = playerinfo.getLatency();
        text = (getOptions().showModName ? title : "") + ping + "ms";
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        renderBackground(stack, pos, dummy);
        this.drawStandardText(stack, pos, dummy);
    }
}
