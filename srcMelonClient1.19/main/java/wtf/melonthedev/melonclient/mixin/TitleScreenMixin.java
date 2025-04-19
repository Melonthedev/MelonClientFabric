package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.screens.SkinCapeChangeScreen;
import wtf.melonthedev.melonclient.gui.screens.clientmenu.MelonClientMenuScreen;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.utils.MelonLogoTexture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {


    @Shadow @Final private static ResourceLocation MINECRAFT_LOGO;

    @Shadow @Final private static ResourceLocation MINECRAFT_EDITION;

    @Shadow @Final private static ResourceLocation PANORAMA_OVERLAY;

    @Shadow @Final public static CubeMap CUBE_MAP;

    @Mutable
    @Shadow @Final private boolean minceraftEasterEgg;

    @Shadow @Nullable private String splash;

    @Shadow public abstract void render(PoseStack poseStack, int i, int j, float f);

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void initInject(CallbackInfo info) {
        Client.getInstance().getDiscordRP().updateMainMenu();
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 48 + 24 * 2, 200, 20, Component.literal("MelonClient"), (p_210872_) ->
                minecraft.setScreen(new MelonClientMenuScreen(this))));
        this.addRenderableWidget(new Button(this.width - this.width / 8 - 40, this.height / 4 + 132, 80, 20, Component.literal("Skin & Cape"), button -> {
            minecraft.setScreen(new SkinCapeChangeScreen(this));
        }));
        //minceraftEasterEgg = true;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;drawString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"))
    public void drawVersionString(PoseStack stack, Font font, String str, int x, int y, int color) {
        //MelonClient Titlescreen Info
        StringBuilder s = new StringBuilder("Minecraft " + SharedConstants.getCurrentVersion().getName() + " " + Client.getInstance().NAMEVVERSION);
        if (font.width(s.toString()) + 10 > width / 2) s = new StringBuilder("MC " + SharedConstants.getCurrentVersion().getName() + " " + Client.getInstance().NAMEVVERSION);
        if (this.minecraft.isDemo()) s.append(" Demo");
        else if (font.width(s + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType())) + 10 < this.width / 2) {
            s.append("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
        }
        drawString(stack, font, s.toString(), x, y, color);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void drawMelonClientLogo(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        MelonClientWrapper.renderLogo((TitleScreen)(Object)this, poseStack);
        if (this.splash != null) {
            poseStack.pushPose();
            poseStack.translate((double)(this.width / 2 + 90), 70.0D, 0.0D);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
            float o = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
            o = o * 100.0F / (float)(this.font.width(this.splash) + 32);
            poseStack.scale(o, o, o);
            drawCenteredString(poseStack, this.font, this.splash, 0, -8, 16776960);
            poseStack.popPose();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"))
    public void renderSplash(PoseStack stack, Font font, String s, int x, int y, int z) {
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    public GuiEventListener addRenderableWidgetRedirect(TitleScreen instance, GuiEventListener widget) {
        if (!(widget instanceof Button button)) return this.addRenderableWidget((GuiEventListener & Widget & NarratableEntry) widget);
        System.out.println(button.getMessage());
        if (button.getMessage().equals(Component.translatable("narrator.button.language"))
                || button.getMessage().equals(Component.translatable("narrator.button.accessibility"))) return widget;
        if (button.getMessage().equals(Component.translatable("menu.quit"))) {
            return this.addRenderableWidget(new Button(this.width / 2 + 2, instance.height / 4 + 48 + 72 + 12, 98, 20, Component.translatable("menu.quit"), (p_96786_) -> Client.setScreen(new ConfirmScreen((flag) -> {
                if (flag) minecraft.stop();
                else minecraft.setScreen(new TitleScreen());
            }, Component.translatable("menu.quit"), Component.literal("Are you sure you want to quit the game?")))));
        }
        this.addRenderableWidget((GuiEventListener & Widget & NarratableEntry) widget);
        return widget;
    }

    @Redirect(method = "createNormalMenuOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener removeRealmsButton(TitleScreen instance, GuiEventListener guiEventListener) {
        if (guiEventListener instanceof Button button && button.getMessage().equals(Component.translatable("menu.online"))) return guiEventListener;
        return addRenderableWidget((GuiEventListener & Widget & NarratableEntry) guiEventListener);
    }

    /**
     * @author Melonthedev
     * @reason Stop Realm Symbol on MelonClient Button
     */
    @Overwrite
    private boolean realmsNotificationsEnabled() {
        return false;
    }

    /**
     * @author Melonthedev
     * @reason Preloading Melonclient Logo
     */
    @Overwrite
    public static CompletableFuture<Void> preloadResources(TextureManager p_96755_, Executor p_96756_) {
        return CompletableFuture.allOf(p_96755_.preload(MINECRAFT_LOGO, p_96756_), p_96755_.preload(MINECRAFT_EDITION, p_96756_), p_96755_.preload(PANORAMA_OVERLAY, p_96756_), CUBE_MAP.preload(p_96755_, p_96756_), p_96755_.preload(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, p_96756_));
    }


}
