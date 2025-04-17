package wtf.melonthedev.melonclient.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {

    /**
     * @author Melonthedev
     * @reason Tab Name Gamemode-Creative Underline (Like Spectator is Italic)
     */
    @Overwrite
    private Component decorateName(PlayerInfo p_94552_, MutableComponent p_94553_) {
        return p_94552_.getGameMode() == GameType.SPECTATOR ? p_94553_.withStyle(ChatFormatting.ITALIC) : (p_94552_.getGameMode() == GameType.CREATIVE ? p_94553_.withStyle(ChatFormatting.UNDERLINE) : p_94553_);
    }

    //TODO: Tab icon

}
