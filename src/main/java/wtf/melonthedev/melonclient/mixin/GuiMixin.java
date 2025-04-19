package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.modengine.rendering.HudManager;

@Mixin(Gui.class)
public abstract class GuiMixin {

    /**
     * @author Melonthedev
     * @reason Melon Client Mod Renderer
     */
    @Inject(method = "render", at = @At("HEAD"))
    private void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        HudManager.getINSTANCE().onRender(guiGraphics);
    }


}
