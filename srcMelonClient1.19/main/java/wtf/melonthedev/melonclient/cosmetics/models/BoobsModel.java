package wtf.melonthedev.melonclient.cosmetics.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BoobsModel<T extends LivingEntity> extends AgeableListModel<T> {
    private final ModelPart rightBoob;
    private final ModelPart leftBoob;

    public BoobsModel(ModelPart root) {
        this.rightBoob = root.getChild("right_boob");
        this.leftBoob = root.getChild("left_boob");
    }


    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        CubeDeformation cubedeformation = new CubeDeformation(1.0F);
        //partdefinition.addOrReplaceChild("right_boob", CubeListBuilder.create().texOffs(22, 0).mirror().addBox(0.0F, -15.0F, 1.0F, 10.0F, 20.0F, 2.0F, cubedeformation), PartPose.offsetAndRotation(0F, -50.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        //partdefinition.addOrReplaceChild("left_boob", CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, -15.0F, 1.0F, 10.0F, 20.0F, 2.0F, cubedeformation), PartPose.offsetAndRotation(0F, -50.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_boob", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(3.0F, 0.0F, -10.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6), /*.addBox(-2.0F, -11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),*/ PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_boob", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -10.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6),/*.addBox(-2.0F, -11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),*/ PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.leftBoob, this.rightBoob);
    }

    @Override
    public void setupAnim(T p_102544_, float p_102545_, float p_102546_, float p_102547_, float p_102548_, float p_102549_) {
        float f = 0.2617994F;
        float f1 = -0.2617994F;
        float f2 = 0.0F;
        float f3 = 0.0F;
        if (p_102544_.isFallFlying()) {
            float f4 = 1.0F;
            Vec3 vec3 = p_102544_.getDeltaMovement();
            if (vec3.y < 0.0D) {
                Vec3 vec31 = vec3.normalize();
                f4 = 1.0F - (float)Math.pow(-vec31.y, 1.5D);
            }

            f = f4 * 0.34906584F + (1.0F - f4) * f;
            f1 = f4 * (-(float)Math.PI / 2F) + (1.0F - f4) * f1;
        } else if (p_102544_.isCrouching()) {
            f = 0.6981317F;
            f1 = (-(float)Math.PI / 4F);
            f2 = 3.0F;
            f3 = 0.08726646F;
        }

        this.leftBoob.y = f2;
        if (p_102544_ instanceof AbstractClientPlayer abstractclientplayer) {
            abstractclientplayer.elytraRotX += (f - abstractclientplayer.elytraRotX) * 0.1F;
            abstractclientplayer.elytraRotY += (f3 - abstractclientplayer.elytraRotY) * 0.1F;
            abstractclientplayer.elytraRotZ += (f1 - abstractclientplayer.elytraRotZ) * 0.1F;
            this.leftBoob.xRot = abstractclientplayer.elytraRotX;
            this.leftBoob.yRot = abstractclientplayer.elytraRotY;
            this.leftBoob.zRot = abstractclientplayer.elytraRotZ;
        } else {
            this.leftBoob.xRot = f;
            this.leftBoob.zRot = f1;
            this.leftBoob.yRot = f3;
        }

        this.rightBoob.yRot = -this.leftBoob.yRot;
        this.rightBoob.y = this.leftBoob.y;
        this.rightBoob.xRot = this.leftBoob.xRot;
        this.rightBoob.zRot = -this.leftBoob.zRot;
    }
}
