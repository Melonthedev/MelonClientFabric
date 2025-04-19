package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.screens.MelonClientMultiplayerScreen;
import wtf.melonthedev.melonclient.gui.screens.clientmenu.MelonClientMenuScreen;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        Button button1 = this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, Component.translatable("menu.shareToLan"), (p_96321_) -> {
            this.minecraft.setScreen(new ShareToLanScreen(this));
        }));
        button1.active = this.minecraft.hasSingleplayerServer() && !this.minecraft.getSingleplayerServer().isPublished();
        this.addRenderableWidget(new Button(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, Component.literal("Multiplayer"), (p_96333_) -> {
            this.minecraft.setScreen(new MelonClientMultiplayerScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20, Component.literal("MelonClient"), (p_96321_) -> {
            this.minecraft.setScreen(new MelonClientMenuScreen(this));
        }));
    }

    @Redirect(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    public GuiEventListener addRenderableWidgetRedirect(PauseScreen instance, GuiEventListener widget) {
        if (!(widget instanceof Button button)) return this.addRenderableWidget((GuiEventListener & Widget & NarratableEntry) widget);
        if (Client.isDevModeEnabled()) System.out.println(button.getMessage());
        if (button.getMessage().equals(Component.translatable("menu.sendFeedback"))
                || button.getMessage().equals(Component.translatable("menu.reportBugs"))
                || button.getMessage().equals(Component.translatable("menu.playerReporting"))
                || button.getMessage().equals(Component.translatable("menu.shareToLan"))) return widget;
        this.addRenderableWidget((GuiEventListener & Widget & NarratableEntry) widget);
        return widget;
    }

}
