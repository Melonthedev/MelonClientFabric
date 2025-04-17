package wtf.melonthedev.melonclient.cosmetics.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class BoobsLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE_BOOBS = ResourceLocation.parse("textures/entity/boobs.png");
    //private final BoobsModel<T> boobsModel;

    public BoobsLayer(RenderLayerParent<T, M> ttt, EntityModelSet p_174494_) {
        super(ttt);
        //this.boobsModel = new BoobsModel<>(p_174494_.bakeLayer(ModelLayers.BOOBS));
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource p_116952_, int p_116953_, T p_116954_, float p_116955_, float p_116956_, float p_116957_, float p_116958_, float p_116959_, float p_116960_) {
        stack.pushPose();
        stack.translate(0.0D, 0.0D, 0.125D);
        //this.getParentModel().copyPropertiesTo(this.boobsModel);
        //this.boobsModel.setupAnim(p_116954_, p_116955_, p_116956_, p_116958_, p_116959_, p_116960_);
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(p_116952_, RenderType.armorCutoutNoCull(TEXTURE_BOOBS), false);
        //this.boobsModel.renderToBuffer(stack, vertexconsumer, p_116953_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
    }
}
