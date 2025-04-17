package wtf.melonthedev.melonclient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button GuiComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiComponent.class)
public interface GuiComponentAccessor {

    /*@Invoker("hLine")
    void hLine(PoseStack stack, int x1, int x2, int y, int color);

    @Invoker("vLine")
    void vLine(PoseStack stack, int x1, int x2, int y, int color);*/

}
