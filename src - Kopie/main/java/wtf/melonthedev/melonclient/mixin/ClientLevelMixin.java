package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void ClientLevel(CallbackInfo ci) {
        Client.getInstance().startIngame();
    }


}
