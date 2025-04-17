package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.melonthedev.melonclient.gui.screens.SkinCapeChangeScreen;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Shadow @Final protected EntityRenderDispatcher entityRenderDispatcher;

    /**
     * @author Melonthedev
     * @reason MelonClient Own Nametag
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;shouldShowName(Lnet/minecraft/world/entity/Entity;)Z"))
    public boolean shouldShowNameRedirect(EntityRenderer entityRenderer, Entity entity) {
        return entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity && !(Minecraft.getInstance().screen instanceof SkinCapeChangeScreen);
    }

    /*@Overwrite
    public void render(Entity p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        if (this.shouldShowName(p_114485_) && !(Minecraft.getInstance().screen instanceof SkinCapeChangeScreen)) {
            this.renderNameTag(p_114485_, p_114485_.getDisplayName(), p_114488_, p_114489_, p_114490_);
        }
    }*/

}

@Mixin(EntityRenderer.class)
interface EntityRendererAccessor {
    @Accessor
    EntityRenderDispatcher getEntityRenderDispatcher();
}
