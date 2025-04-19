package wtf.melonthedev.melonclient.mixin.accessors;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Player.class)
public interface PlayerAccessor {

    @Accessor
    GameProfile getGameProfile();


}
