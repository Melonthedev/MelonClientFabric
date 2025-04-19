package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {

    @Accessor
    static int getFps() {
        throw new AssertionError();
    }

    @Accessor
    TextureManager getTextureManager();
}
