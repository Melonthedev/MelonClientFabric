package wtf.melonthedev.melonclient.modengine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import wtf.melonthedev.melonclient.modengine.rendering.IRenderer;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.utils.FileManager;

import java.io.File;
import java.nio.file.Path;

public abstract class ModuleDraggable extends Module implements IRenderer {

    protected ModOptions options;
    public String dummy = "Dummy";
    public String text;
    public String title;
    public String name = "MelonClientMod";

    public ModuleDraggable() {
        this.options = loadOptionsFromFile();
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
    public void save(ModOptions options) {
        this.options = options;
        saveOptionsToFile();
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        super.setEnabled(isEnabled);
        loadOptionsFromFile();
        options.enabled = isEnabled;
        saveOptionsToFile();
    }

    @Override
    public boolean isEnabled() {
        return options.enabled;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    private File getFolder() {
        File folder = new File(FileManager.getModsDir(), this.getClass().getSimpleName());
        folder.mkdirs();
        return folder;
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (dummy == null || dummy.equals("")) return;
        renderBackground(stack, pos, dummy);
        drawStandardText(guiGraphics, pos, dummy);
    }

    private ModOptions loadOptionsFromFile() {
        ModOptions loaded = FileManager.readFromJson(new File(getFolder(), "options.json"), ModOptions.class);
        if (loaded == null) {
            loaded = new ModOptions();
            loaded.init();
            saveOptionsToFile();
        }
        loaded.checkForUninitializedOptions();
        this.options = loaded;
        return loaded;
    }

    public void saveOptionsToFile() {
        FileManager.writeJsonToFile(new File(getFolder(), "options.json"), options);
    }

    @Override
    public ModOptions load() {
        return options;
    }

    public void updateDummy() {}

    public void scale(float scale) {
        this.options.scale = scale;
    }

    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    public final int getLineOffset(int lineNum) {
     return (mc.font.lineHeight + 3) * lineNum;
    }

    public void drawStandardText(GuiGraphics guiGraphics, ScreenPosition pos, String text) {
        guiGraphics.drawCenteredString(mc.font, text, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFF);
        //mc.font.draw(stack, text, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFF);
    }

    public void drawStandardText(GuiGraphics guiGraphics, String text, int x, int y) {
        guiGraphics.drawCenteredString(mc.font, text, x + 1, y + 1, options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFF);
        //mc.font.draw(stack, text, x, y, options.color.getColor() != null ? options.color.getColor() : 0xFF00FFFF);
    }

    public ModOptions getOptions() {
        return options;
    }

    public void renderBackground(PoseStack stack, ScreenPosition pos, String text) {
        if (options.backgroundColor == null) return;
        fill(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY() - 2, pos.getAbsoluteX() + mc.font.width(text) + 2, pos.getAbsoluteY() + mc.font.lineHeight + 2, options.backgroundColor.getColor());
    }

    public void renderBackground(PoseStack stack, String text, int x, int y) {
        if (options.backgroundColor == null) return;
        fill(stack, x - 2, y - 2, x + mc.font.width(text) + 2, y + mc.font.lineHeight + 2, options.backgroundColor.getColor());
    }
    public void renderBackground(PoseStack stack, int width, int x, int y) {
        if (options.backgroundColor == null) return;
        fill(stack, x - 2, y - 2, x + width, y + mc.font.lineHeight + 2, options.backgroundColor.getColor());
    }
    public void renderBackground(PoseStack stack, int width, int height, int x, int y) {
        if (options.backgroundColor == null) return;
        fill(stack, x - 2, y - 2, x + width, y + height, options.backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, String text, int x, int y) {
        if (options.backgroundColor == null) return;
        fill(stack, x - 2, y, x + mc.font.width(text) + 2, y + mc.font.lineHeight, options.backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, int width, int x, int y) {
        if (options.backgroundColor == null) return;
        fill(stack, x - 2, y, x + width, y + mc.font.lineHeight, options.backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, ScreenPosition pos, String text) {
        if (options.backgroundColor == null) return;
        fill(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY(), pos.getAbsoluteX() + mc.font.width(text) + 2, pos.getAbsoluteY() + mc.font.lineHeight, options.backgroundColor.getColor());
    }
    public void renderSmallBackground(PoseStack stack, ScreenPosition pos, int width) {
        if (options.backgroundColor == null) return;
        fill(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY(), pos.getAbsoluteX() + width, pos.getAbsoluteY() + mc.font.lineHeight, options.backgroundColor.getColor());}

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
        //RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.addVertex((float)p_93107_, (float)p_93110_, 0.0F).setColor(f, f1, f2, f3);
        bufferbuilder.addVertex((float)p_93109_, (float)p_93110_, 0.0F).setColor(f, f1, f2, f3);
        bufferbuilder.addVertex((float)p_93109_, (float)p_93108_, 0.0F).setColor(f, f1, f2, f3);
        bufferbuilder.addVertex((float)p_93107_, (float)p_93108_, 0.0F).setColor(f, f1, f2, f3);
        BufferUploader.drawWithShader(bufferbuilder.build());
        //RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}