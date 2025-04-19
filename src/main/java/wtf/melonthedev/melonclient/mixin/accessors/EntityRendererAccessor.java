package wtf.melonthedev.melonclient.mixin.accessors;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderer.class)
interface EntityRendererAccessor {
    @Accessor
    EntityRenderDispatcher getEntityRenderDispatcher();
}
