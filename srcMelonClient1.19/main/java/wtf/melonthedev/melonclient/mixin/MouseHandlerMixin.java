package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.Blaze3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.util.SmoothDouble;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.modengine.ModInstanceManager;
import wtf.melonthedev.melonclient.utils.ClientSettings;
import wtf.melonthedev.melonclient.utils.MelonZoomUtils;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Shadow public abstract boolean isMouseGrabbed();

    @Shadow @Final private SmoothDouble smoothTurnX;

    @Shadow @Final private SmoothDouble smoothTurnY;

    @Shadow private double accumulatedDX;

    @Shadow private double accumulatedDY;

    @Shadow @Final private Minecraft minecraft;

    @Shadow private double lastMouseEventTime;

    @Redirect(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"))
    private void onScrollNormalCase(Inventory instance, double d) {
        if (ClientSettings.keyMelonZoom.isDown()) {
            MelonZoomUtils.handleZoomFactor((int) d);
            return;
        }
        if (Client.getMinecraft().player == null) return;
        Client.getMinecraft().player.getInventory().swapPaint(d);
    }

    /**
     * @author Melonthedev
     * @reason MelonClient Zoom
     */
    @Overwrite
    public void turnPlayer() {
        double d0 = Blaze3D.getTime();
        double d1 = d0 - this.lastMouseEventTime;
        this.lastMouseEventTime = d0;
        if (this.isMouseGrabbed() && this.minecraft.isWindowActive()) {
            double d4 = this.minecraft.options.sensitivity().get() * (double)0.6F + (double)0.2F;
            double d5 = d4 * d4 * d4;
            double d6 = d5 * 8.0D;
            double d2;
            double d3;
            int i = 1;
            if (this.minecraft.options.smoothCamera) {
                double d7 = this.smoothTurnX.getNewDeltaValue(this.accumulatedDX * d6, d1 * d6);
                double d8 = this.smoothTurnY.getNewDeltaValue(this.accumulatedDY * d6, d1 * d6);
                d2 = d7;
                d3 = d8;
            } else if (this.minecraft.options.getCameraType().isFirstPerson() && this.minecraft.player.isScoping()) {
                this.smoothTurnX.reset();
                this.smoothTurnY.reset();
                d2 = this.accumulatedDX * d5;
                d3 = this.accumulatedDY * d5;
            } else if (ClientSettings.keyMelonZoom.isDown()) {
                this.smoothTurnX.reset();
                this.smoothTurnY.reset();
                d2 = this.accumulatedDX * d5 * MelonZoomUtils.zoomFactor * 10;
                d3 = this.accumulatedDY * d5 * MelonZoomUtils.zoomFactor * 10;
            } else {
                this.smoothTurnX.reset();
                this.smoothTurnY.reset();
                d2 = this.accumulatedDX * d6;
                d3 = this.accumulatedDY * d6;
            }

            this.accumulatedDX = 0.0D;
            this.accumulatedDY = 0.0D;
            if (minecraft.options.invertYMouse().get()) {
                i = -1;
            }

            /*if (ModInstanceManager.modPerspective != null && ModInstanceManager.modPerspective.isToggled) {
                ModInstanceManager.modPerspective.camYaw += d3 * 0.15F;
                ModInstanceManager.modPerspective.camPitch += d2 * 0.15F;
                if (ModInstanceManager.modPerspective.camPitch > 90) ModInstanceManager.modPerspective.camPitch = 90;
                if (ModInstanceManager.modPerspective.camPitch < -90) ModInstanceManager.modPerspective.camPitch = -90;
                return;
            }*/

            this.minecraft.getTutorial().onMouse(d2, d3);
            if (this.minecraft.player != null) {
                this.minecraft.player.turn(d2, d3 * (double)i);
            }

        } else {
            this.accumulatedDX = 0.0D;
            this.accumulatedDY = 0.0D;
        }
    }


}
