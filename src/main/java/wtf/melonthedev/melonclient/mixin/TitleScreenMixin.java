package wtf.melonthedev.melonclient.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
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
    protected TitleScreenMixin(Component component) {
        super(component);
    }

    /**
     * @author Melonthedev
     * @reason Add Melonclient Buttons
     */
    @Inject(method = "init", at = @At("TAIL"))
    public void initInject(CallbackInfo info) {
        Client.getInstance().getDiscordRP().updateMainMenu();
        this.addRenderableWidget(Button.builder(Component.literal("MelonClient"), (p_210872_) ->
                minecraft.setScreen(new MelonClientMenuScreen(this))).bounds(this.width / 2 - 100, this.height / 4 + 48 + 24 * 2, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Skin & Cape"), button -> {
            minecraft.setScreen(new SkinCapeChangeScreen(this));
        }).bounds(this.width - this.width / 8 - 40, this.height / 4 + 132, 80, 20).build());
    }

    /**
     * @author Melonthedev
     * @reason MelonClient Version String
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"))
    public int drawVersionString(GuiGraphics instance, Font font, String string, int i, int j, int k) {

        StringBuilder s = new StringBuilder("Minecraft " + SharedConstants.getCurrentVersion().getName() + " " + Client.getInstance().NAMEVVERSION);
        if (font.width(s.toString()) + 10 > width / 2) s = new StringBuilder("MC " + SharedConstants.getCurrentVersion().getName() + " " + Client.getInstance().NAMEVVERSION);
        if (this.minecraft.isDemo()) s.append(" Demo");
        else if (font.width(s + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType())) + 10 < this.width / 2) {
            s.append("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
        }
        instance.drawString(font, s.toString(), i, j, k);
        return 1;
    }

    /**
     * @reason Render Logo
     */
    @Inject(method = "render", at = @At(value = "TAIL"))
    public void drawMelonClientLogo(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci, @Local(name = "g") float alphaFade) {
        MelonClientWrapper.renderMainScreenLogo(guiGraphics, alphaFade);
    }

    /**
     * @author Melonthedev
     * @reason Edit Main Buttons & Add Quit Game Confirmation Dialog
     * Removes unnecessary Accessibility & Language Buttons, which can be accessed from options menu
     */
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    public GuiEventListener addRenderableWidgetRedirect(TitleScreen instance, GuiEventListener widget) {
        if (!(widget instanceof Button button)) return this.addRenderableWidget((GuiEventListener & Renderable & NarratableEntry) widget);
        System.out.println(button.getMessage());
        if (button.getMessage().equals(Component.translatable("options.language"))
                || button.getMessage().equals(Component.translatable("options.accessibility"))) return widget;
        if (button.getMessage().equals(Component.translatable("menu.quit"))) {
            return this.addRenderableWidget(Button.builder(Component.translatable("menu.quit"), (p_96786_) -> Client.setScreen(new ConfirmScreen((flag) -> {
                if (flag) minecraft.stop();
                else minecraft.setScreen(new TitleScreen());
            }, Component.translatable("menu.quit"), Component.literal("Are you sure you want to quit the game?")))).bounds(this.width / 2 + 2, instance.height / 4 + 48 + 72 + 12, 98, 20).build());
        }
        this.addRenderableWidget((GuiEventListener & Renderable & NarratableEntry) widget);
        return widget;
    }


    /**
     * @author Melonthedev
     * @reason Stop Realm Button from rendering
     */
    @Redirect(method = "createNormalMenuOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener removeRealmsButton(TitleScreen instance, GuiEventListener guiEventListener) {
        if (guiEventListener instanceof Button button && button.getMessage().equals(Component.translatable("menu.online"))) return guiEventListener;
        return addRenderableWidget((GuiEventListener & Renderable & NarratableEntry) guiEventListener);
    }

    /**
     * @author Melonthedev
     * @reason Stop Realm Symbol on MelonClient Button THROWS EXCEPTION CAUSES mc to be null somewhere
     */
    //@Overwrite
    //private boolean realmsNotificationsEnabled() {
    //    return false;
    //}

    /**
     * @author Melonthedev
     * @reason Preloading Melonclient Logo
     */
    @Overwrite
    public static CompletableFuture<Void> preloadResources(TextureManager p_96755_, Executor p_96756_) {
        return CompletableFuture.allOf(p_96755_.preload(LogoRenderer.MINECRAFT_LOGO, p_96756_), p_96755_.preload(LogoRenderer.MINECRAFT_EDITION, p_96756_), p_96755_.preload(PanoramaRenderer.PANORAMA_OVERLAY, p_96756_), CUBE_MAP.preload(p_96755_, p_96756_), p_96755_.preload(MelonLogoTexture.MELONCLIENT_LOGO_LOCATION, p_96756_));
    }


}
