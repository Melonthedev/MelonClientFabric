package wtf.melonthedev.melonclient.modengine.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import wtf.melonthedev.melonclient.modengine.Mod;
import wtf.melonthedev.melonclient.modengine.rendering.IRenderer;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.utils.ClientUtils;
import wtf.melonthedev.melonclient.utils.FileManager;

import java.io.File;

public abstract class ModDraggable extends Mod implements IRenderer {

    public String dummy = "Dummy";
    public String text;
    public String title;

    public ModDraggable(String name, String id) {
        super(name, id);
        ClientUtils.logDev("Ich bin ModDraggable " + id + " und wurde gerade initialisiert!!");
    }

    @Override
    public int getHeight() {
        return mc.font.lineHeight;
    }

    @Override
    public int getWidth() {
        return mc.font.width(dummy);
    }

    @Override
    public boolean isEnabled() {
        return getOptions().enabled;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (dummy == null || dummy.equals("")) return;
        renderBackground(guiGraphics.pose(), pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }

    @Override
    protected void loadOptionsFromFile() {
        ModDraggableOptions loadedOptions = FileManager.readFromJson(new File(getModFolder(), OPTIONS_FILE_NAME), ModDraggableOptions.class);
        if (loadedOptions == null) {
            loadedOptions = new ModDraggableOptions();
            loadedOptions.init();
            saveOptionsToFile();
        }
        loadedOptions.checkForUninitializedOptions();
        this.options = loadedOptions;
    }

    @Override
    public ModDraggableOptions load() {
        return getOptions();
    }

    @Override
    public void save(ModDraggableOptions options) {
        super.save();
    }

    public void updateDummy() {}

    public void scale(float scale) {
        this.getOptions().scale = scale;
    }

    public ModDraggableOptions getOptions() {
        return (ModDraggableOptions) options;
    }

    public ScreenPosition getPosition() {
        return getOptions().position;
    }

    public void setPosition(ScreenPosition newPosition) {
        getOptions().position = newPosition;
        save();
    }

    // Rendering

    public final int getLineOffset(int lineNum) {
        return (mc.font.lineHeight + 3) * lineNum;
    }
    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    public void drawStandardText(GuiGraphics guiGraphics, ScreenPosition pos, String text) {
        guiGraphics.drawString(mc.font, text, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, getOptions().color.getColor() != null ? getOptions().color.getColor() : 0xFF00FFFF);
    }
    public void drawStandardText(GuiGraphics guiGraphics, String text, int x, int y) {
        guiGraphics.drawString(mc.font, text, x + 1, y + 1, getOptions().color.getColor() != null ? getOptions().color.getColor() : 0xFF00FFFF);
    }

    public void renderBackground(PoseStack stack, ScreenPosition pos, String text) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY() - 2, pos.getAbsoluteX() + mc.font.width(text) + 2, pos.getAbsoluteY() + mc.font.lineHeight + 2, getOptions().backgroundColor.getColor());
    }
    public void renderBackground(PoseStack stack, String text, int x, int y) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, x - 2, y - 2, x + mc.font.width(text) + 2, y + mc.font.lineHeight + 2, getOptions().backgroundColor.getColor());
    }
    public void renderBackground(PoseStack stack, int width, int x, int y) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, x - 2, y - 2, x + width, y + mc.font.lineHeight + 2, getOptions().backgroundColor.getColor());
    }
    public void renderBackground(PoseStack stack, int width, int height, int x, int y) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, x - 2, y - 2, x + width, y + height, getOptions().backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, String text, int x, int y) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, x - 2, y, x + mc.font.width(text) + 2, y + mc.font.lineHeight, getOptions().backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, int width, int x, int y) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, x - 2, y, x + width, y + mc.font.lineHeight, getOptions().backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, ScreenPosition pos, String text) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY(), pos.getAbsoluteX() + mc.font.width(text) + 2, pos.getAbsoluteY() + mc.font.lineHeight, getOptions().backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, ScreenPosition pos, int width) {
        if (getOptions().backgroundColor == null) return;
        fill(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY(), pos.getAbsoluteX() + width, pos.getAbsoluteY() + mc.font.lineHeight, getOptions().backgroundColor.getColor());}

    public static void fill(PoseStack p_93173_, int p_93174_, int p_93175_, int p_93176_, int p_93177_, int p_93178_) {
        innerFill(p_93173_.last().pose(), p_93174_, p_93175_, p_93176_, p_93177_, p_93178_);
    }

    private static void innerFill(Matrix4f poseStack, int p_93107_, int p_93108_, int p_93109_, int p_93110_, int p_93111_) {
        if (p_93107_ < p_93109_) {
            int i = p_93107_;
            p_93107_ = p_93109_;
            p_93109_ = i;
        }
        if (p_93108_ < p_93110_) {
            int j = p_93108_;
            p_93108_ = p_93110_;
            p_93110_ = j;
        }
        float f3 = (float)(p_93111_ >> 24 & 255) / 255.0F;
        float f = (float)(p_93111_ >> 16 & 255) / 255.0F;
        float f1 = (float)(p_93111_ >> 8 & 255) / 255.0F;
        float f2 = (float)(p_93111_ & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.addVertex((float)p_93107_, (float)p_93110_, 0.0F).setColor(f, f1, f2, f3);
        bufferbuilder.addVertex((float)p_93109_, (float)p_93110_, 0.0F).setColor(f, f1, f2, f3);
        bufferbuilder.addVertex((float)p_93109_, (float)p_93108_, 0.0F).setColor(f, f1, f2, f3);
        bufferbuilder.addVertex((float)p_93107_, (float)p_93108_, 0.0F).setColor(f, f1, f2, f3);
        BufferUploader.drawWithShader(bufferbuilder.build());
        RenderSystem.disableBlend();
    }
}