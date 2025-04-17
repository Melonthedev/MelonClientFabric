package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
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

    @Shadow @Final private static ResourceLocation GUI_BARS_LOCATION;

    @Shadow protected abstract void drawBar(PoseStack poseStack, int i, int j, BossEvent bossEvent);

    /**
     * @author Melonthedev
     * @reason For Mod Bossbar
     */
    @Overwrite
    public void render(PoseStack p_93705_) {
        if (!this.events.isEmpty()) {
            int x = ModInstanceManager.modBossbar.getOptions().position.getAbsoluteX();
            int y = ModInstanceManager.modBossbar.getOptions().position.getAbsoluteY();
            int i = this.minecraft.getWindow().getGuiScaledWidth();
            int j = 12;
            int nextY = y;

            for(LerpingBossEvent lerpingbossevent : this.events.values()) {
                //int k = i / 2 - 91;
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, GUI_BARS_LOCATION);
                this.drawBar(p_93705_, x + 2, nextY + 11, lerpingbossevent);
                Component component = lerpingbossevent.getName();
                int l = this.minecraft.font.width(component);
                int i1 = x + 91 + 2 - l / 2;
                //int j1 = j - 9;
                this.minecraft.font.drawShadow(p_93705_, component, (float)i1, (float)nextY + 2, 16777215);
                j += 10 + 9;
                nextY += 10 + 9;
                if (j >= this.minecraft.getWindow().getGuiScaledHeight() / 3) {
                    break;
                }
            }

        }
    }

}
