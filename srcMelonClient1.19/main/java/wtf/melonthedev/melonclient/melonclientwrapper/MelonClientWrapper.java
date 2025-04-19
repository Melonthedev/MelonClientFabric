package wtf.melonthedev.melonclient.melonclientwrapper;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.mixin.MinecraftAccessor;

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

    public static final ResourceLocation MELONCLIENT_LOGO = ResourceLocation.parse("textures/gui/title/melonclient.png");
    public static void renderLogo(TitleScreen screen, PoseStack p_96739_) {
        RenderSystem.setShaderTexture(0, MELONCLIENT_LOGO);
        int j2 = (int)((double) getMinecraft().getWindow().getGuiScaledWidth() * 0.5D);
        int x1 = j2 - 100;
        int y1 = 61;
        int width = 100;
        int height = 50;
        //screen.blitOutlineBlack(x1, y1, (x, y) -> {
            GuiComponent.blit(p_96739_, x1, y1, width, height, -0.0625F, 0.0F, 120, 60, 120, 120);
            GuiComponent.blit(p_96739_, x1 + 100, y1, width, height, 0.0625F, 60.0F, 120, 60, 120, 120); //stack, x, y, width, height, texturezoom x and y
        //});
    }

    public static void renderEntityInInventory(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_, boolean disableRotateBounds) {
        float f = (float)Math.atan((double)(p_98854_ / 40.0F));
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
        Lighting.setupFor3DItems();
    }

    public static void renderDummyBossHealthAtPosition(PoseStack stack, GuiComponent screen, int x, int y) {
        LerpingBossEvent lerpingbossevent = new LerpingBossEvent(UUID.randomUUID(), Component.literal("Ender Dragon"), 100, BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS, false, false, false);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ResourceLocation.parse("textures/gui/bars.png"));
        screen.blit(stack, x + 2, y + 11, 0, lerpingbossevent.getColor().ordinal() * 5 * 2, 182, 5);
        int i2 = (int)(lerpingbossevent.getProgress() * 183.0F);
        if (i2 > 0) screen.blit(stack, x + 2, y + 11, 0, lerpingbossevent.getColor().ordinal() * 5 * 2 + 5, 182, 5);
        Component component = lerpingbossevent.getName();
        int l = Client.getMinecraft().font.width(component);
        int i1 = x + 91 + 2 - l / 2;
        Client.getMinecraft().font.drawShadow(stack, component, (float)i1, (float)y + 2, 16777215);
    }

}
