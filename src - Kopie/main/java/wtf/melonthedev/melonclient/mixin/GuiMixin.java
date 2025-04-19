package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.melonthedev.melonclient.modengine.rendering.HudManager;
import wtf.melonthedev.melonclient.modengine.ModInstanceManager;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow protected abstract void renderEffects(PoseStack poseStack);

    @Shadow @Final private Minecraft minecraft;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    private void renderEffects(Gui instance, PoseStack poseStack) {
        //Melon Client Mod Renderer
        HudManager.getINSTANCE().onRender(poseStack);
        renderEffects(poseStack);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    public void renderBossHealthOverlay(BossHealthOverlay instance, PoseStack poseStack) {
        if (ModInstanceManager.modBossbar != null && ModInstanceManager.modBossbar.isEnabled()) {
            instance.render(poseStack);
        }
    }


}
