package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.obfuscate.DontObfuscate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ClientBrandRetriever.class, remap = false)
public class ClientBrandRetrieverMixin {

    /**
     * @author Melonthedev
     * @reason MelonClient Brand
     */
    @Overwrite
    @DontObfuscate
    public static String getClientModName() {
        return "melonclient";
    }


}
