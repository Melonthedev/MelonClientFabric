package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(LightTexture.class)
public class LightTextureMixin {

    /*@Redirect(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/NativeImage;setPixelRGBA(III)V"))
    public void updateLightTexture(NativeImage nativeImage, int x, int y, int color) {
        nativeImage.setPixelRGBA(x, y, new Color(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), 255).getRGB());
    }*/

}
