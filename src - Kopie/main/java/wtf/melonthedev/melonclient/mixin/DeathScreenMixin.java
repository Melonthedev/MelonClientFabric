package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;

import static net.minecraft.client.gui.GuiComponent.drawCenteredString;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {

    @Inject(method = "render", at = @At("TAIL"))
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        Minecraft minecraft = Client.getMinecraft();
        drawCenteredString(poseStack, minecraft.font, "Death Location:" + ChatFormatting.DARK_RED +
                " X: " + (int) minecraft.player.blockPosition().getX() +
                " Y: " + (int) minecraft.player.blockPosition().getY() +
                " Z: " + + (int) minecraft.player.blockPosition().getZ(),
                minecraft.screen.width / 2, 115, 16777215);
    }

}
