package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.utils.RenderUtil;

import java.util.Objects;

@Mixin(WinScreen.class)
public abstract class WinScreenMixin {

    @Shadow protected abstract void addEmptyLine();

    @Shadow protected abstract void addCreditsLine(Component component, boolean bl);

    /**
     * @author Melonthedev
     * @reason Add MelonClient Credits to Credits screen
     */
    @Inject(method = "addCreditsFile", at = @At("HEAD"))
    public void addCreditsFileInject(CallbackInfo info) {
        for (Component line : Client.getInstance().credits) {
            if (Objects.equals(line, Component.empty())) {
                addEmptyLine();
                continue;
            }
            boolean centered = line.getString().contains("!C!");
            addCreditsLine(centered ? Component.literal(line.getString().replaceAll("!C!", "")).withStyle(line.getStyle()) : line, centered);
        }
        addEmptyLine();
        addEmptyLine();
    }

    /**
     * @author Melonthedev
     * @reason Render MelonClient Logo on Credits screen
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/LogoRenderer;renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V"))
    public void renderLogoRedirect(LogoRenderer instance, GuiGraphics guiGraphics, int i, float f, int j) {
        instance.renderLogo(guiGraphics, i, f, j);
        RenderUtil.drawMelonClientLogo(guiGraphics, i/2-100, j + 50, 200, 50);
    }

}

