package wtf.melonthedev.melonclient.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {

    /**
     * @author Melonthedev
     * @reason MelonClient Death Location
     */
    @Inject(method = "render", at = @At("TAIL"))
    public void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        Minecraft minecraft = Client.getMinecraft();
        guiGraphics.drawCenteredString(minecraft.font, "Death Location:" + ChatFormatting.DARK_RED +
                " X: " + (int) minecraft.player.blockPosition().getX() +
                " Y: " + (int) minecraft.player.blockPosition().getY() +
                " Z: " + + (int) minecraft.player.blockPosition().getZ(),
                minecraft.screen.width / 2, 115, 16777215);
    }

}
