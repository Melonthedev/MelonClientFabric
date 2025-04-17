package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import wtf.melonthedev.melonclient.mods.ModInstanceManager;

import java.util.Map;
import java.util.UUID;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {

    @Shadow @Final private Map<UUID, LerpingBossEvent> events;

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void drawBar(GuiGraphics guiGraphics, int i, int j, BossEvent bossEvent);

    /**
     * @author Melonthedev
     * @reason For Mod Bossbar
     */
    @Overwrite
    public void render(GuiGraphics guiGraphics) {
        if (ModInstanceManager.modBossbar == null || !ModInstanceManager.modBossbar.isEnabled()) {
            return;
        }
        if (this.events.isEmpty()) {
            return;
        }
        int x = ModInstanceManager.modBossbar.getOptions().position.getAbsoluteX();
        int y = ModInstanceManager.modBossbar.getOptions().position.getAbsoluteY();

        int nextY = y;

        this.minecraft.getProfiler().push("bossHealth");
        int i = guiGraphics.guiWidth();
        int j = 12;
        for (LerpingBossEvent lerpingBossEvent : this.events.values()) {
            //int k = i / 2 - 91;
            //int l = j;
            this.drawBar(guiGraphics, x + 2, nextY + 11, lerpingBossEvent);
            Component component = lerpingBossEvent.getName();
            int m = this.minecraft.font.width(component);
            int n = x + 91 + 2 - m / 2;
            //int o = l - 9;
            guiGraphics.drawString(this.minecraft.font, component, n, nextY + 2, 0xFFFFFF);
            nextY += 10 + 9;
            if ((j += 10 + this.minecraft.font.lineHeight) < guiGraphics.guiHeight() / 3) continue;
            break;
        }
        this.minecraft.getProfiler().pop();
    }

}
