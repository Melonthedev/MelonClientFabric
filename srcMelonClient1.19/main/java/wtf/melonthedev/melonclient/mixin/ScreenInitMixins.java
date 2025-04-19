package wtf.melonthedev.melonclient.mixin;

import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;

public abstract class ScreenInitMixins extends Screen {
    private ScreenInitMixins(Component component) {
        super(component);
    }
}

@Mixin(SelectWorldScreen.class)
class SelectWorldScreenMixin {
    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo info) {
        Client.getInstance().getDiscordRP().updateWorldSelection();
    }
}

@Mixin(JoinMultiplayerScreen.class)
class JoinMultiplayerScreenMixin extends Screen {
    @Shadow
    @Final
    private Screen lastScreen;

    protected JoinMultiplayerScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo info) {
        if (lastScreen != null)
            Client.getInstance().getDiscordRP().updateMultiplayerSelectionMenu();
        this.addRenderableWidget(new Button(5, 5, 40, 20, Component.literal("Realms"), (btn) -> this.minecraft.setScreen(new RealmsMainScreen(this))));
    }
}
