package wtf.melonthedev.melonclient.gui.screens.clientmenu.options;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.entity.player.Player;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.screens.clientmenu.MelonClientMenuScreen;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientOptionsScreen extends MelonScreen {

    public MelonClientOptionsScreen(Screen parent) {
        super(Component.literal(Client.getInstance().NAME + " Options"), parent, true);
        shouldDrawDoneButton(true);
    }

    @Override
    public void init()
    {
        super.init();
        this.addRenderableWidget(new Button(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20, Component.literal("Teleport 5b"), (p_96321_) -> {
            Player p = minecraft.player;
            double x = p.getX();
            double y = p.getX();
            double z = p.getX();
            float yaw = p.getYRot();
            float pitch = p.getXRot();
            p.setPos(x, y, z);
            this.minecraft.getConnection().send(new ClientboundPlayerPositionPacket(x + 1, y, z, yaw, pitch, null, 0, true));
        }));
        /*this.addRenderableWidget(new MelonOptionButton(width / 2 - 155, height / 5 - 6, 150, 20, "MelonClient Tab Icon: On", "MelonClient Tab Icon: Off", GameSettings.Options.CLIENTTABICON, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 - 155, height / 5 - 6 + 25, 150, 20, "Fullbright: On", "Fullbright: Off", GameSettings.Options.FULLBRIGHT, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 - 155, height / 5 - 6 + 50, 150, 20, "Splash Text: On", "Splash Text: Off", GameSettings.Options.SPLASHTEXTENABLED, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 - 155, height / 5 - 6 + 75, 150, 20, "Custom Menu Panorama: On", "Custom Menu Panorama: Off", GameSettings.Options.CUSTOMPANORAMA, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 - 155, height / 5 - 6 + 100, 150, 20, "Panorama Blur: On", "Panorama Blur: Off", GameSettings.Options.PANORAMABLURENABLED, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 + 5, height / 5 - 6, 150, 20, "Show Own Nametag: On", "Show Own Nametag: Off", GameSettings.Options.OWNNAMETAGVISIBLE, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 + 5, height / 5 - 6 + 25, 150, 20, "Default Tab Header: On", "Default Tab Header: Off", GameSettings.Options.USEDEFAULTTABINFOHEADER, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 + 5, height / 5 - 6 + 50, 150, 20, "Aqua Splash Screen: On", "Aqua Splash Screen: Off", GameSettings.Options.USEAQUASPLASHSCREEN, this));
        this.addRenderableWidget(new MelonOptionButton(width / 2 + 5, height / 5 - 6 + 75, 150, 20, "Pumpkin Blur: Off", "Pumpkin Blur: On", GameSettings.Options.DISABLEPUMPKINBLUR, this));*/


        //buttonList.add(new GuiButton(1, width / 2 - 155, height / 5 - 6, 150, 20, "Textures: " + (mc.gameSettings.melonNewTextures ? "New" : "Old")));
        /*buttonList.add(new GuiButton(2, width / 2 - 155, height / 5 + 24 - 6, 150, 20, "FullBright: " + (mc.gameSettings.melonFullbright ? "On" : "Off")));
        buttonList.add(new GuiButton(3, width / 2 - 155, height / 5 + 48 - 6, 150, 20, "MelonClient Tab Icon: " + (mc.gameSettings.melonClientTabIcon ? "On" : "Off")));
        buttonList.add(new GuiButton(4, width / 2 - 155, height / 5 + 72 - 6, 150, 20, "Transp. InvBackground: " + (mc.gameSettings.melonBackgroundInventoryTransparent ? "On" : "Off")));
        buttonList.add(new GuiButton(5, width / 2 - 155, height / 5 + 96 - 6, 150, 20, "Transp. MenuBackground: " + (mc.gameSettings.melonBackgroundMenuTransparent ? "On" : "Off")));
        buttonList.add(new GuiButton(6, width / 2 - 155, height / 5 + 120 - 6, 150, 20, "BlockOverlay"));
        buttonList.add(new GuiButton(7, width / 2 - 155, height / 5 + 144 - 6, 150, 20, "Disable SlotScrolling: " + (mc.gameSettings.melonScrollSlotChangeDisabled ? "On" : "Off")));

        buttonList.add(new GuiButton(8, width / 2 + 5, height / 5 - 6, 150, 20, "User Interface"));
        buttonList.add(new GuiButton(9, width / 2 + 5, height / 5 + 24 - 6, 150, 20, "Discord Rich Presence"));
        buttonList.add(new GuiButton(10, width / 2 + 5, height / 5 + 48 - 6, 150, 20, "Video Settings Presets"));
        buttonList.add(new GuiButton(11, width / 2 + 5, height / 5 + 72 - 6, 150, 20, "Splash Text: " + (mc.gameSettings.melonSplashtextEnabled ? "On" : "Off")));
        buttonList.add(new GuiButton(12, width / 2 + 5, height / 5 + 96 - 6, 150, 20, "Custom Menu Panorama: " + (mc.gameSettings.melonCustompanorama ? "On" : "Off")));
        buttonList.add(new GuiButton(13, width / 2 + 5, height / 5 + 120 - 6, 150, 20, "Panorama Blur: " + (mc.gameSettings.melonPanoramablurEnabled ? "On" : "Off")));
        //buttonList.add(new GuiOptionSlider(13, width / 2 + 5, height / 5 + 120 - 6, GameSettings.Options.PANORAMABLUR, 0, 10));
        //buttonList.add(new GuiButton(14, width / 2 + 5, height / 5 + 144 - 6, 150, 20, "Custom Sky: " + (mc.gameSettings.melonCustomSky ? "On" : "Off")));

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 5 + 168, LocalKeyTranslator.format("gui.done")));*/
    }

    /*** Called by the controls from the buttonList when activated. (Mouse pressed for buttons) */
    //protected void actionPerformed(GuiButton button) {
        //super.actionPerformed(button);
        /*if (!button.enabled) return;
        switch (button.id) {
            case 200:
                Client.setScreen(this.parent);
                break;
            case 1:
                //if (mc.gameSettings.melonNewTextures) System.out.println("New Textures ON");
                //else System.out.println("New Textures OFF");
                //mc.gameSettings.melonNewTextures = !mc.gameSettings.melonNewTextures;
                //button.displayString = "Textures: " + (mc.gameSettings.melonNewTextures ? "New" : "Old");
                break;
            case 2:
                if (mc.gameSettings.melonFullbright) mc.gameSettings.saturation = 1.0f;
                else mc.gameSettings.saturation = 10.0f;
                mc.gameSettings.melonFullbright = !mc.gameSettings.melonFullbright;
                button.displayString = "FullBright: " + (mc.gameSettings.melonFullbright ? "On" : "Off");
                break;
            case 3:
                if (mc.gameSettings.melonClientTabIcon) System.out.println("TabIcon");
                else System.out.println("TabIcon");
                mc.gameSettings.melonClientTabIcon = !mc.gameSettings.melonClientTabIcon;
                button.displayString = "MelonClient Tab Icon: " + (mc.gameSettings.melonClientTabIcon ? "On" : "Off");
                break;
            case 4:
                mc.gameSettings.melonBackgroundInventoryTransparent = !mc.gameSettings.melonBackgroundInventoryTransparent;
                button.displayString = "Transp. InvBackground: " + (mc.gameSettings.melonBackgroundInventoryTransparent ? "On" : "Off");
                break;
            case 5:
                mc.gameSettings.melonBackgroundMenuTransparent = !mc.gameSettings.melonBackgroundMenuTransparent;
                button.displayString = "Transp. MenuBackground: " + (mc.gameSettings.melonBackgroundMenuTransparent ? "On" : "Off");
                break;
            case 7:
                mc.gameSettings.melonScrollSlotChangeDisabled = !mc.gameSettings.melonScrollSlotChangeDisabled;
                button.displayString = "Disable SlotScrolling: " + (mc.gameSettings.melonScrollSlotChangeDisabled ? "On" : "Off");
                break;
            case 11:
                mc.gameSettings.melonSplashtextEnabled = !mc.gameSettings.melonSplashtextEnabled;
                button.displayString = "Splash Text: " + (mc.gameSettings.melonSplashtextEnabled ? "On" : "Off");
                break;
            case 12:
                mc.gameSettings.melonCustompanorama = !mc.gameSettings.melonCustompanorama;
                button.displayString = "Custom Menu Panorama: " + (mc.gameSettings.melonCustompanorama ? "On" : "Off");
                break;
            case 13:
                //GuiOptionSlider slider = (GuiOptionSlider) button;
                //System.out.println(slider.displayString);
                //slider.mousePressed(mc,  mc.mouseHelper.deltaX, mc.mouseHelper.deltaY);
                mc.gameSettings.melonPanoramablurEnabled = !mc.gameSettings.melonPanoramablurEnabled;
                button.displayString = "Panorama Blur: " + (mc.gameSettings.melonPanoramablurEnabled ? "On" : "Off");
                break;
            case 14:
                //if (mc.gameSettings.melonCustomSky) System.out.println("Custom Sky");
                //else System.out.println("Custom Sky");
                //mc.gameSettings.melonCustomSky = !mc.gameSettings.melonCustomSky;
                //button.displayString = "Custom Sky: " + (mc.gameSettings.melonCustomSky ? "On" : "Off");
                break;
        }
        this.mc.gameSettings.saveOptions();*/
    //}

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.drawCenteredString(stack, this.font, "Ingame:", this.width / 2 - 80, this.height / 9, 16777215);
        this.drawCenteredString(stack, this.font, "Others:", this.width / 2 + 80, this.height / 9, 16777215);
    }

}
