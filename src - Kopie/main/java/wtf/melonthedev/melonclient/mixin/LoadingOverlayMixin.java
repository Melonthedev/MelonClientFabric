package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.GameRenderer;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.utils.MelonLogoTexture;

import java.util.Optional;
import java.util.function.Consumer;

import static net.minecraft.client.gui.GuiComponent.blit;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {

    @Shadow @Final private boolean fadeIn;
    @Shadow private long fadeInStart;
    @Shadow private long fadeOutStart;
    @Shadow @Final private ReloadInstance reload;
    @Shadow private float currentProgress;

    @Shadow protected abstract void drawProgressBar(PoseStack poseStack, int i, int j, int k, int l, float f);

    @Shadow @Final private Consumer<Optional<Throwable>> onFinish;

    //@Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"))
    //public int render(IntSupplier intSupplier) {
    //    return FastColor.ARGB32.color(0, 0, 255, 255);
    //}


    private static int replaceAlpha(int i, int j) {
        return i & 16777215 | j << 24;
    }

    @Inject(method = "registerTextures", at = @At("TAIL"))
    private static void registerTextures(CallbackInfo ci) {
        Client.getMinecraft().getTextureManager().register(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, new MelonLogoTexture());
    }


    /**
     * @author Melonthedev
     * @reason Adding MelonClient Splash Screen
     */
    @Overwrite
    public void render(PoseStack p_96178_, int p_96179_, int p_96180_, float p_96181_) {
        Minecraft minecraft = Client.getMinecraft();
        int i = minecraft.getWindow().getGuiScaledWidth();
        int j = minecraft.getWindow().getGuiScaledHeight();
        long k = Util.getMillis();
        if (this.fadeIn && this.fadeInStart == -1L) {
            this.fadeInStart = k;
        }

        float f = this.fadeOutStart > -1L ? (float)(k - this.fadeOutStart) / 1000.0F : -1.0F;
        float f1 = this.fadeInStart > -1L ? (float)(k - this.fadeInStart) / 500.0F : -1.0F;
        float f2;
        if (f >= 1.0F) {
            if (minecraft.screen != null) {
                minecraft.screen.render(p_96178_, 0, 0, p_96181_);
            }

            int l = Mth.ceil((1.0F - Mth.clamp(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
            GuiComponent.fill(p_96178_, 0, 0, i, j, replaceAlpha(FastColor.ARGB32.color(255, 0, 255, 255), l));
            f2 = 1.0F - Mth.clamp(f - 1.0F, 0.0F, 1.0F);
        } else if (this.fadeIn) {
            if (minecraft.screen != null && f1 < 1.0F) {
                minecraft.screen.render(p_96178_, p_96179_, p_96180_, p_96181_);
            }

            int l1 = Mth.ceil(Mth.clamp((double)f1, 0.15D, 1.0D) * 255.0D);
            GuiComponent.fill(p_96178_, 0, 0, i, j, replaceAlpha(FastColor.ARGB32.color(255, 0, 255, 255), l1));
            f2 = Mth.clamp(f1, 0.0F, 1.0F);
        } else {
            int i2 = FastColor.ARGB32.color(255, 0, 255, 255);
            float f3 = (float)(i2 >> 16 & 255) / 255.0F;
            float f4 = (float)(i2 >> 8 & 255) / 255.0F;
            float f5 = (float)(i2 & 255) / 255.0F;
            GlStateManager._clearColor(f3, f4, f5, 1.0F);
            GlStateManager._clear(16384, Minecraft.ON_OSX);
            f2 = 1.0F;
        }

        int j2 = (int)((double)minecraft.getWindow().getGuiScaledWidth() * 0.5D);
        int k2 = (int)((double)minecraft.getWindow().getGuiScaledHeight() * 0.5D);
        int height = minecraft.getWindow().getGuiScaledHeight();
        double d1 = Math.min((double)minecraft.getWindow().getGuiScaledWidth() * 0.75D, (double)minecraft.getWindow().getGuiScaledHeight()) * 0.25D;
        int i1 = (int)(d1 * 0.5D);
        double d0 = d1 * 4.0D;
        int j1 = (int)(d0 * 0.5D);
        RenderSystem.setShaderTexture(0, ResourceLocation.parse("textures/gui/title/mojangstudios.png"));
        RenderSystem.enableBlend();
        RenderSystem.blendEquation(32774);
        RenderSystem.blendFunc(770, 1);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f2);
        blit(p_96178_, j2 - j1, height/2, j1, (int)d1, -0.0625F, 0.0F, 120, 60, 120, 120);
        //blit(p_96178_, j2 - j1, k2 - i1 - 50, j1, (int)d1, -0.0625F, 0.0F, 120, 60, 120, 120);
        blit(p_96178_, j2, height/2, j1, (int)d1, 0.0625F, 60.0F, 120, 60, 120, 120);
        //blit(p_96178_, j2, k2 - i1 - 50, j1, (int)d1, 0.0625F, 60.0F, 120, 60, 120, 120);
        RenderSystem.setShaderTexture(0, MelonLogoTexture.MELONCLIENT_LOGO_LOCATION);
        RenderSystem.defaultBlendFunc();
        RenderSystem.bindTextureForSetup(0);
            //blit(p_96178_, j2 - j1, k2 - i1 + 40 - 0, j1, (int)d1, -0.0625F, 0.0F, 120, 60, 120, 120);
            blit(p_96178_, j2 - j1, height/4 - 15, j1, (int)d1, -0.0625F, 0.0F, 120, 60, 120, 120);
            //blit(p_96178_, j2, k2 - i1 + 40 - 0, j1, (int)d1, 0.0625F, 60.0F, 120, 60, 120, 120);
            blit(p_96178_, j2, height/4 - 15, j1, (int)d1, 0.0625F, 60.0F, 120, 60, 120, 120);

        RenderSystem.disableBlend();
        //int k1 = (int)((double)this.minecraft.getWindow().getGuiScaledHeight() * 0.8325D);
        int k1 = (int)((double)minecraft.getWindow().getGuiScaledHeight() /2) + (int)((double)minecraft.getWindow().getGuiScaledHeight() /4) + 20;
        float f6 = this.reload.getActualProgress();
        this.currentProgress = Mth.clamp(this.currentProgress * 0.95F + f6 * 0.050000012F, 0.0F, 1.0F);
        if (f < 1.0F) {
            this.drawProgressBar(p_96178_, i / 2 - j1, k1 - 5, i / 2 + j1, k1 + 5, 1.0F - Mth.clamp(f, 0.0F, 1.0F));
        }

        if (f >= 2.0F) {
            minecraft.setOverlay(null);
        }

        if (this.fadeOutStart == -1L && this.reload.isDone() && (!this.fadeIn || f1 >= 2.0F)) {
            try {
                this.reload.checkExceptions();
                this.onFinish.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.onFinish.accept(Optional.of(throwable));
            }

            this.fadeOutStart = Util.getMillis();
            if (minecraft.screen != null) {
                minecraft.screen.init(minecraft, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
            }
        }
        p_96178_.pushPose();
        p_96178_.scale(2, 2, 2);
        p_96178_.popPose();

    }

}
