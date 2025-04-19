package wtf.melonthedev.melonclient.cosmetics;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;

public abstract class CosmeticBase extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public CosmeticBase(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> p_117346_, EntityModelSet p_174494_) {
        super(p_117346_);
    }
    @Override
    public void render(PoseStack stack, MultiBufferSource p_117350_, int partitalTicks, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!player.isInvisible()) renderCosmetic(stack, player, limbSwing, limbSwingAmount, partitalTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public abstract void renderCosmetic(PoseStack stack, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale);

}
