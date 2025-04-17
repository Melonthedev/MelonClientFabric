package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import wtf.melonthedev.melonclient.gui.screens.SkinCapeChangeScreen;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Shadow protected abstract boolean shouldShowName(Entity entity);

    @Shadow protected abstract void renderNameTag(Entity entity, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);

    /**
     * @author Melonthedev
     * @reason MelonClient Own Nametag
     */
    @Overwrite
    public void render(Entity p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        if (this.shouldShowName(p_114485_) && !(Minecraft.getInstance().screen instanceof SkinCapeChangeScreen)) {
            this.renderNameTag(p_114485_, p_114485_.getDisplayName(), p_114488_, p_114489_, p_114490_);
        }
    }

}

@Mixin(EntityRenderer.class)
interface EntityRendererAccessor {
    @Accessor
    EntityRenderDispatcher getEntityRenderDispatcher();
}
