package wtf.melonthedev.melonclient.mixin.accessors;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {

    /*@Accessor // DEPRECATED in favour of public getFps
    static int getFps() {
        throw new AssertionError();
    }

    @Accessor
    TextureManager getTextureManager();*/
}
