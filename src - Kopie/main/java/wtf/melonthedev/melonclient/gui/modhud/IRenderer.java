package wtf.melonthedev.melonclient.modengine.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public interface IRenderer extends IRendererConfig {

    int getWidth();
    int getHeight();

    void render(ScreenPosition pos, GuiGraphics guiGraphics);

    default void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {render(pos, stack, guiGraphics);}

    default boolean isEnabled() {
        return true;
    }
}
