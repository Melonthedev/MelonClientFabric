package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.screens.SelectCapeScreen;
import wtf.melonthedev.melonclient.gui.screens.SkinCapeChangeScreen;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    /**
     * @author Melonthedev
     * @reason MelonClient Show Own Nametag
     */
    @Overwrite
    public boolean shouldShowName(LivingEntity p_115333_) {
        double d0 = ((EntityRendererAccessor) this).getEntityRenderDispatcher().distanceToSqr(p_115333_);
        float f = p_115333_.isDiscrete() ? 32.0F : 64.0F;
        if (d0 >= (double)(f * f)) {
            return false;
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localplayer = minecraft.player;
            boolean flag = !p_115333_.isInvisibleTo(localplayer);
            //if (p_115333_ != localplayer) {

            Team team = p_115333_.getTeam();
            Team team1 = localplayer.getTeam();
            if (team != null) {
                Team.Visibility team$visibility = team.getNameTagVisibility();
                switch (team$visibility) {
                    case ALWAYS:
                        return flag;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return team1 == null ? flag : team.isAlliedTo(team1) && (team.canSeeFriendlyInvisibles() || flag);
                    case HIDE_FOR_OWN_TEAM:
                        return team1 == null ? flag : !team.isAlliedTo(team1) && flag;
                    default:
                        return true;
                }
            }

            Screen s = Client.getMinecraft().screen;
            if (s instanceof SkinCapeChangeScreen || s instanceof TitleScreen || s instanceof SelectCapeScreen || s instanceof PauseScreen) return false; //TODO: Clean this up with intern melonscreen drawplayer method
            return Minecraft.renderNames() && flag && !p_115333_.isVehicle() && p_115333_ != minecraft.getCameraEntity(); //p_115333_ != minecraft.getCameraEntity() &&
        }
    }

}
