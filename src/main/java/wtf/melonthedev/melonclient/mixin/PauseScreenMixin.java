package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.melonthedev.melonclient.gui.screens.MelonClientMultiplayerScreen;
import wtf.melonthedev.melonclient.gui.screens.clientmenu.MelonClientMenuScreen;
import wtf.melonthedev.melonclient.utils.ClientUtils;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component component) {
        super(component);
    }

    /**
     * @author Melonthedev
     * @reason Stop certain PauseScreen Buttons from rendering to make space for custom ones & render them instead
     *
     */
    @Redirect(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
    public <T extends LayoutElement> T addChildRedirect(GridLayout.RowHelper instance, T layoutElement) {
        if (!(layoutElement instanceof Button button)) return instance.addChild(layoutElement);

        ClientUtils.logDev("Created Pausescreen Button: " + button.getMessage().getString());


        // Replacing buttons with custom ones
        if (button.getMessage().equals(Component.translatable("menu.sendFeedback"))
            || button.getMessage().equals(Component.translatable("menu.feedback"))) {
            Button melonClientButton = Button.builder(Component.literal("MelonClient"), (p_96321_) -> this.minecraft.setScreen(new MelonClientMenuScreen(this))).bounds(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20).build();
            return (T) instance.addChild(melonClientButton);
        } else if (button.getMessage().equals(Component.translatable("menu.reportBugs"))) {
            //return instance.addChild(this.openScreenButton(FEEDBACK_SUBSCREEN, () -> new PauseScreen.FeedbackSubScreen(this)));
            return instance.addChild(layoutElement);
        } else if (button.getMessage().equals(Component.translatable("menu.playerReporting"))) {
            Button multiplayerButton = Button.builder(Component.literal("Multiplayer"), (p_96333_) -> this.minecraft.setScreen(new MelonClientMultiplayerScreen())).bounds(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20).build();
            return (T) instance.addChild(multiplayerButton);
        } else if (button.getMessage().equals(Component.translatable("menu.shareToLan"))) {
            return instance.addChild(layoutElement); // Keep the original button
        } else {
            return instance.addChild(layoutElement);
        }
    }

}
