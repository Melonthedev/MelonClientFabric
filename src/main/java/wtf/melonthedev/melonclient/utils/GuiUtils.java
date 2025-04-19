package wtf.melonthedev.melonclient.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.utils.dummyplayer.DummyPlayer;

public class GuiUtils {

    private GuiUtils() {}
    private static final Minecraft mc = Client.getMinecraft();
    public static PoseStack stack = new PoseStack();

    public static void drawMelonString(GuiGraphics guiGraphics) {
        guiGraphics.drawString(mc.font, "MelonClient", guiGraphics.guiWidth() - mc.font.width("MelonClient") - 4, guiGraphics.guiHeight() - mc.font.lineHeight - 4, 16777215);
    }

    public static void drawHollowRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int color) {
        guiGraphics.hLine(x, x + width, y, color);
        guiGraphics.hLine(x, x + width, y + height, color);
        guiGraphics.vLine(x, y + height, y, color);
        guiGraphics.vLine(x + width, y, y + height, color);
    }

    public static void drawTexture(GuiGraphics guiGraphics, int posX, int posY, int width, int height, ResourceLocation texture) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);
        guiGraphics.blit(texture, posX, posY, width, height, 128, 64);
    }

    public static void renderRotatingPlayerInInventory(GuiGraphics guiGraphics, int x, int y, int scale) {
        int rotation = (int) ((Util.getMillis() % 10000L) / 10000.0f * 180);
        renderPlayerInInventory(guiGraphics, x, y, scale, rotation);
    }

    public static void renderPlayerInInventory(GuiGraphics guiGraphics, int x, int y, int scale, int rotation) {
        LivingEntity entity;
        if (mc.player != null) {
            entity = mc.player;
        } else {
            entity = new DummyPlayer(null, new GameProfile(mc.getUser().getProfileId(), "COCK"));
        }
        renderEntityInInventory(guiGraphics, x, y, scale, rotation, entity);
    }

    public static void renderEntityInInventory(GuiGraphics guiGraphics, float x, float y, float scale, float rotationDegrees, LivingEntity entity) {
        Quaternionf bodyRotation = new Quaternionf().rotateZ((float) Math.PI);

        float originalBodyRot = entity.yBodyRot;
        float originalYRot = entity.getYRot();
        float originalXRot = entity.getXRot();
        float originalHeadRotO = entity.yHeadRotO;
        float originalHeadRot = entity.yHeadRot;

        entity.yBodyRot = 180.0f + rotationDegrees * 2;
        entity.setYRot(180.0f + rotationDegrees * 2);
        entity.setXRot(-10f);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();

        float entityScale = entity.getScale();
        Vector3f translation = new Vector3f(0.0f, entity.getBbHeight() / 2.0f + 0.0625f * entityScale, 0.0f);
        float scaled = scale / entityScale;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x, y, 50.0);
        guiGraphics.pose().scale(scaled, scaled, -scaled);
        guiGraphics.pose().translate(translation.x, translation.y, translation.z);
        guiGraphics.pose().mulPose(bodyRotation);

        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        dispatcher.overrideCameraOrientation(new Quaternionf().rotateY((float) Math.PI));
        dispatcher.setRenderShadow(false);

        RenderSystem.runAsFancy(() -> {
            dispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880);
        });

        guiGraphics.flush();
        dispatcher.setRenderShadow(true);
        guiGraphics.pose().popPose();
        Lighting.setupFor3DItems();

        entity.yBodyRot = originalBodyRot;
        entity.setYRot(originalYRot);
        entity.setXRot(originalXRot);
        entity.yHeadRotO = originalHeadRotO;
        entity.yHeadRot = originalHeadRot;
    }

}
