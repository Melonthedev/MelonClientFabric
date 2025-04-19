package wtf.melonthedev.melonclient.cosmetics.pieces;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.cosmetics.CosmeticBase;
import wtf.melonthedev.melonclient.cosmetics.CosmeticController;
import wtf.melonthedev.melonclient.cosmetics.CosmeticModelBase;

public class CosmeticBoobs extends CosmeticBase {

    private static final ResourceLocation BOOBS_LOCATION = ResourceLocation.parse("textures/entity/boobs.png");
    //private final BoobsModel boobsModel;

    public CosmeticBoobs(PlayerRenderer renderer, EntityRendererProvider.Context context) {
        super(renderer, context.getModelSet());
        //this.boobsModel = new BoobsModel(context.getModelSet().bakeLayer(ModelLayers.BOOBS));
    }

    @Override
    public void renderCosmetic(PoseStack stack, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

    }

    @Override
    public void render(PoseStack stack, MultiBufferSource mbfs, int p_117351_, AbstractClientPlayer player, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (CosmeticController.shouldRender()) {
            stack.pushPose();
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(mbfs, RenderType.armorCutoutNoCull(BOOBS_LOCATION), false);
            //boobsModel.renderToBuffer(stack, vertexconsumer, p_117351_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            stack.popPose();
        }
    }

    private class BoobsModel extends CosmeticModelBase {

        ModelPart leftBoob;
        ModelPart rightBoob;
        //ModelPart righdtBoob;

        public BoobsModel(ModelPart root) {
            leftBoob = root.getChild("left_boob");
            rightBoob = root.getChild("right_boob");
            //righdtBoob = root.getChild("righdt_boob");
        }

        public static LayerDefinition createLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();
            partdefinition.addOrReplaceChild("left_boob", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(0.0F, 0.0F, 0.0F));
            partdefinition.addOrReplaceChild("right_boob", CubeListBuilder.create().texOffs(0, 0)        .addBox(0.0F, 0.0F, 0.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(0.0F, 0.0F, 0.0F));
            //partdefinition.addOrReplaceChild("righdt_boob", CubeListBuilder.create().texOffs(0, 0)        .addBox(5.0F, 0.0F, 0.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(0.0F, 0.0F, 0.0F));
            return LayerDefinition.create(meshdefinition, 32, 32);
        }

        @Override
        protected Iterable<ModelPart> headParts() {
            return ImmutableList.of();
        }

        @Override
        protected Iterable<ModelPart> bodyParts() {
            return ImmutableList.of(leftBoob, rightBoob);
        }
    }

}
