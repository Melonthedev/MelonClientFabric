package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.event.events.ClientTickEvent;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow public abstract @Nullable ServerData getCurrentServer();

    /**
     *
     * @author Melonthedev
     * @reason MelonClient Window Title
     */
    @Redirect(method = "updateTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;createTitle()Ljava/lang/String;"))
    public String createTitle(Minecraft instance) {
        final Window window = Minecraft.getInstance().getWindow();
        window.setTitle(Client.getInstance().NAMEVERSION);
        //window.setIcon();
        StringBuilder stringbuilder = new StringBuilder(Client.getInstance().NAMEVERSION);
        ClientPacketListener clientpacketlistener = Client.getMinecraft().getConnection();
        if (clientpacketlistener != null && clientpacketlistener.getConnection().isConnected()) {
            stringbuilder.append(" - ");
            ServerData serverData = this.getCurrentServer();
            if (Client.getMinecraft().hasSingleplayerServer()) {
                stringbuilder.append(I18n.get("title.singleplayer"));
            } else if (serverData != null && serverData.isRealm()) {
                stringbuilder.append(I18n.get("title.multiplayer.realms"));
            } else if (!Client.getMinecraft().hasSingleplayerServer() && (Client.getMinecraft().getCurrentServer() == null || !Client.getMinecraft().getCurrentServer().isLan())) {
                MelonClientWrapper.getMultiplayerTitle(stringbuilder);
            } else {
                stringbuilder.append(I18n.get("title.multiplayer.lan"));
            }
        }
        return stringbuilder.toString();
    }

    /**
     * @reason Show Welcome Screen on first MelonClient Start
     */
    @Redirect(method ="<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    public void setScreen(Minecraft instance, net.minecraft.client.gui.screens.Screen screen) {
        Client.openStartScreen();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void stop(CallbackInfo ci) {
        Client.getInstance().shutdown();
    }


    /*@Inject(method = "reloadResourcePacks*", at = @At("TAIL"))
    public void reloadResourcePacks(boolean bl, Object gameLoadCookie, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.getReturnValue().thenRun(Client::onResourceReload);
    }*/ //TODO

    @Inject(method = "runTick", at = @At("TAIL"))
    public void runTick(boolean bl, CallbackInfo ci) {
        new ClientTickEvent().call();
        Client.onTick();
    }

}
