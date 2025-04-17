package wtf.melonthedev.melonclient.melonclientwrapper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.mixin.MinecraftAccessor;
import wtf.melonthedev.melonclient.utils.RenderUtil;

import java.util.UUID;

public class MelonClientWrapper {

    private MelonClientWrapper() {}

    public static void setScreen(Screen screen) {
        Client.getMinecraft().setScreen(screen);
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static void openUrl(String url) {
        Util.getPlatform().openUri(url);
    }

    public static StringBuilder getMultiplayerTitle(StringBuilder sb) {
        if (Client.getMinecraft().getCurrentServer() != null) {
            sb.append(Client.getMinecraft().getCurrentServer().ip);
        } else {
            sb.append(I18n.get("title.multiplayer.other"));
        }
        return sb;
    }

    public static int getFps() {
        return MinecraftAccessor.getFps();// Minecraft.fps;
    }

    public static void renderMainScreenLogo(GuiGraphics guiGraphics) {
        int j2 = (int)((double) getMinecraft().getWindow().getGuiScaledWidth() * 0.5D);
        int x = j2 - 100;
        int y = 61;
        int width = 100;
        int height = 50;
        RenderUtil.drawMelonClientLogo(guiGraphics, x, y);
    }

    public static void renderEntityInInventory(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_, boolean disableRotateBounds) {
        /*float f = (float)Math.atan((double)(p_98854_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_98855_ / 40.0F));
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)p_98851_, (double)p_98852_, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float)p_98853_, (float)p_98853_, (float)p_98853_);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        float f2 = p_98856_.yBodyRot;
        float f3 = p_98856_.getYRot();
        float f4 = p_98856_.getXRot();
        float f5 = p_98856_.yHeadRotO;
        float f6 = p_98856_.yHeadRot;
        if (disableRotateBounds) {
            p_98856_.yBodyRot = 180.0F + (p_98854_ / 40) * 20.0F;
            p_98856_.setYRot(180.0F + (p_98854_ / 40) * 20.0F);
        } else {
            p_98856_.yBodyRot = 180.0F + f * 20.0F;
            p_98856_.setYRot(180.0F + f * 40.0F);
        }
        p_98856_.setXRot(-f1 * 20.0F);
        p_98856_.yHeadRot = p_98856_.getYRot();
        p_98856_.yHeadRotO = p_98856_.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Client.getMinecraft().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Client.getMinecraft().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(p_98856_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        p_98856_.yBodyRot = f2;
        p_98856_.setYRot(f3);
        p_98856_.setXRot(f4);
        p_98856_.yHeadRotO = f5;
        p_98856_.yHeadRot = f6;
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();*/
    }

    public static void renderDummyBossHealthAtPosition(GuiGraphics guiGraphics, int x, int y) {
        LerpingBossEvent lerpingbossevent = new LerpingBossEvent(UUID.randomUUID(), Component.literal("Ender Dragon"), 100, BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS, false, false, false);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(ResourceLocation.parse("textures/gui/bars.png"), x + 2, y + 11, 0, lerpingbossevent.getColor().ordinal() * 5 * 2, 182, 5);
        int i2 = (int)(lerpingbossevent.getProgress() * 183.0F);
        if (i2 > 0) guiGraphics.blit(ResourceLocation.parse("textures/gui/bars.png"), x + 2, y + 11, 0, lerpingbossevent.getColor().ordinal() * 5 * 2 + 5, 182, 5);
        Component component = lerpingbossevent.getName();
        int l = Client.getMinecraft().font.width(component);
        int i1 = x + 91 + 2 - l / 2;
        guiGraphics.drawStringWithBackdrop(Client.getMinecraft().font, component, i1, y + 2,1, 16777215); // ?
    }

}
