package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Options.class)
public abstract class OptionsMixin {

    private static Component gvl(Component p_231922_, Component p_231923_) {
        return Component.translatable("options.generic_value", p_231922_, p_231923_);
    }

    private static Component gvl(Component p_231901_, int p_231902_) {
        return gvl(p_231901_, Component.literal(Integer.toString(p_231902_)));
    }

    /**
     * @author Melonthedev
     * @reason MelonClient Fullbright
     */
    @Overwrite
    public OptionInstance<Double> gamma() {
        return new OptionInstance<Double>("options.gamma", OptionInstance.noTooltip(), (p_231913_, p_231914_) -> {
            int i = (int) (p_231914_ * 100.0D);
            if (i == 0) {
                return gvl(p_231913_, Component.translatable("options.gamma.min"));
            } else if (i == 500) {
                return gvl(p_231913_, Component.translatable("options.gamma.default"));
            } else {
                return i == 1000 ? gvl(p_231913_, Component.translatable("options.gamma.max")) : gvl(p_231913_, i);
            }
        }, OptionInstance.UnitDouble.INSTANCE, 0.5D, (p_231877_) -> {
        });
    }


}
