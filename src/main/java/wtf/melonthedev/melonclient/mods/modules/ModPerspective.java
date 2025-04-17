package wtf.melonthedev.melonclient.mods.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CameraType;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.gui.modhud.ScreenPosition;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;
import wtf.melonthedev.melonclient.utils.ClientSettings;
import wtf.melonthedev.melonclient.utils.MelonZoomUtils;

public class ModPerspective extends ModuleDraggable {

    private boolean returnOnRelease = true;
    public boolean isToggled = false;
    public float camYaw = 0;
    public float camPitch = 0;
    private CameraType previouseCameraType = CameraType.FIRST_PERSON;

    public void handleKeyboard() {
        if (ClientSettings.keyMelonPerspective.isDown()) {
            isToggled = !isToggled;
            camYaw = mc.player.getXRot();
            camPitch = mc.player.getYRot();

            if (isToggled) {
                previouseCameraType = mc.options.getCameraType();
                mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            } else {
                mc.options.setCameraType(previouseCameraType);
            }
        } else if (returnOnRelease) {
            isToggled = false;
            mc.options.setCameraType(previouseCameraType);
        }
        if (mc.options.keyTogglePerspective.isDown()) {
            isToggled = false;
        }
    }

    public float getCamYaw() {
        return isToggled ? camYaw : mc.player.getXRot();
    }

    public float getCamPitch() {
        return isToggled ? camPitch : mc.player.getYRot();
    }

    public boolean overrideMouse() {
        if (mc.isWindowActive()) {
            if (!isToggled) {
                return true;
            }


            /*mc.mouseHandler.grabMouse();
            float f1 = mc.options.sensitivity().get().floatValue() * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = (float) mc.mouseHandler.xpos() * f2;
            float f4 = (float) mc.mouseHandler.ypos() * f2;

            camYaw += f3 * 0.15F;
            camPitch += f4 * 0.15F;
            if (camPitch > 90) camPitch = 90;
            if (camPitch < -90) camPitch = -90;*/

        }
        return false;
    }

    public ModPerspective() {
        title = "Free Perspective: ";
        updateDummy();
    }

    @Override
    public void updateDummy() {
        dummy = (getOptions().showModName ? getTitle() : "") + "Toggled";
    }

    @Override
    public void render(ScreenPosition pos, GuiGraphics guiGraphics) {
        if (isToggled)
            text = (getOptions().showModName ? getTitle() : "") + "Toggled";
        else
            text = (getOptions().showModName ? getTitle() : "") + "Disabled";
        renderBackground(guiGraphics.pose(), pos, text);
        drawStandardText(guiGraphics, pos, text);
    }

    @Override
    public void renderDummy(ScreenPosition pos, GuiGraphics guiGraphics) {
        drawStandardText(guiGraphics, pos, dummy);
        renderBackground(guiGraphics.pose(), pos, dummy);
    }
}
