package wtf.melonthedev.melonclient.cosmetics;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class CosmeticModelBase extends AgeableListModel {

    public CosmeticModelBase() {
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return null;
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return null;
    }

    @Override
    public void setupAnim(Entity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }
}
