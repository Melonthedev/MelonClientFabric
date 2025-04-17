package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.capes.CapeManager;
import wtf.melonthedev.melonclient.capes.MelonCape;
import wtf.melonthedev.melonclient.capes.MelonCapeAnimated;
import wtf.melonthedev.melonclient.utils.ClientSettings;
import wtf.melonthedev.melonclient.utils.MelonZoomUtils;

import java.util.concurrent.CompletableFuture;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Shadow @Nullable private PlayerInfo playerInfo;

    @Shadow @Nullable protected abstract PlayerInfo getPlayerInfo();

    /**
     * @author Melonthedev
     * @reason MelonClient Cape
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void AbstractClientPlayer(CallbackInfo ci) {
        if (Client.getMinecraft().player == null) return;
        if (Client.getMinecraft().getConnection() == null) {
            System.out.println("Loading Local Cape");
            CompletableFuture.runAsync(() -> CapeManager.loadCape(Client.getMinecraft().player.getGameProfile().getId()));
        } else if (getPlayerInfo() != null) {
            System.out.println("Loading Cape");
            CompletableFuture.runAsync(() -> CapeManager.loadCape(getPlayerInfo().getProfile().getId()));
        }
    }

    /**
     * @author Melonthedev
     * @reason Override CloakTexture to MelonClient Cape
     */
    @Redirect(method = "getSkin", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerInfo;getSkin()Lnet/minecraft/client/resources/PlayerSkin;"))
    public PlayerSkin getSkinRedirect(PlayerInfo playerinfo) {
        MelonCape cape = playerinfo == null ? null : CapeManager.getSelectedCape(playerinfo.getProfile().getId());
        assert playerinfo != null; // Is checked in mc code
        PlayerSkin originalSkin = playerinfo.getSkin();

        if (cape == null) return originalSkin; // TODO Handle melon client cape disabled

        ResourceLocation capeLocation;
        if (cape.isAnimated()) capeLocation = ((MelonCapeAnimated) cape).getCurrentFrameTexture();
        else capeLocation = cape.getTextureLocation();

        return new PlayerSkin(
                originalSkin.texture(),
                originalSkin.textureUrl(),
                capeLocation,
                capeLocation, // ???
                originalSkin.model(),
                originalSkin.secure()
        );
    }


    //public @Nullable ResourceLocation getCloakTextureLocation() {
        //PlayerInfo playerinfo = this.playerInfo;
        //return playerinfo == null ? null : playerinfo.getCapeLocation();
      /*if (!CapeUtils.animatedCapeSelected) {
         return melonClientCapeLocation == null ? (playerinfo == null ? null : playerinfo.getCapeLocation()) : melonClientCapeLocation;
      }
      return CapeUtils.getCurrentLocation();*/

    //}

    //MelonClent Zoom
    @Inject(method = "getFieldOfViewModifier", at = @At("TAIL"), cancellable = true)
    public void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
        if (ClientSettings.keyMelonZoom.isDown())
            cir.setReturnValue(MelonZoomUtils.zoomFactor);
    }

}
