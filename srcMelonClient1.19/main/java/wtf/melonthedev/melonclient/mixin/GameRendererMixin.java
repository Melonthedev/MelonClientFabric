package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow private float oldFov;

    @Shadow private float fov;

    /**
     * @author Melonthedev
     * @reason MelonZoom
     */
    @Overwrite
    private void tickFov() {
        float f = 1.0F;
        if (this.minecraft.getCameraEntity() instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.minecraft.getCameraEntity();
            f = abstractclientplayer.getFieldOfViewModifier();
        }

        this.oldFov = this.fov;
        this.fov += (f - this.fov) * 0.5F;
        if (this.fov > 1.5F) {
            this.fov = 1.5F;
        }

        if (this.fov < 0.01F) {
            this.fov = 0.01F;
        }
    }

}
