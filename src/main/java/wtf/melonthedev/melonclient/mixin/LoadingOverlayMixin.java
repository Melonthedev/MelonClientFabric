package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.utils.MelonLogoTexture;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {

    @Shadow @Final private boolean fadeIn;
    @Shadow private long fadeInStart;
    @Shadow private long fadeOutStart;
    @Shadow @Final private ReloadInstance reload;
    @Shadow private float currentProgress;
    @Shadow @Final private Consumer<Optional<Throwable>> onFinish;

    /**
     * @author Melonthedev
     * @reason Replacing the splash screen backdrop color
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"))
    public int render(IntSupplier intSupplier) {
        return FastColor.ARGB32.color(0, 20, 205, 205);
    }

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "registerTextures", at = @At("TAIL"))
    private static void registerTextures(CallbackInfo ci) {
        Client.getMinecraft().getTextureManager().register(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, new MelonLogoTexture());
    }


    /**
     * @author Melonthedev
     * @reason Adding MelonClient Splash Screen
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIFFIIII)V"))
    public void blit(GuiGraphics guiGraphics, ResourceLocation rl, int i, int j, int k, int l, float f, float g, int m, int n, int o, int p) {
        guiGraphics.blit(rl, i, j, k, l, f, g, m, n, o, p);
        guiGraphics.blit(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, i, j + 60, k, l, f, g, m, n, o, p); // + progressbar runter setzen
    }

}
