package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {

    /*@Accessor // DEPRECATED in favour of public getFps
    static int getFps() {
        throw new AssertionError();
    }

    @Accessor
    TextureManager getTextureManager();*/
}
