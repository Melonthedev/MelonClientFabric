package wtf.melonthedev.melonclient.gui.screens.clientmenuold;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

public class MelonClientGlobalSettingsScreen extends MelonScreen {

    private final Screen parent;
    public String title;

    public MelonClientGlobalSettingsScreen(Screen parent) {
        super(Component.empty(), true);
        this.parent = parent;
    }

    /*** Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the window resizes, the buttonList is cleared beforehand.*/
    public void initGui()
    {
        this.title = Client.getInstance().NAME + " Global Settings";

        /*buttonList.add(new GuiButton(1, width / 2 - 155, height / 5 - 6, 150, 20, "Textures: " + (mc.options.melonNewTextures ? "New" : "Old")));
        buttonList.add(new GuiButton(2, width / 2 - 155, height / 5 + 24 - 6, 150, 20, "FullBright: " + (mc.options.melonFullbright ? "On" : "Off")));
        buttonList.add(new GuiButton(3, width / 2 - 155, height / 5 + 48 - 6, 150, 20, "MelonClient Tab Icon: " + (mc.options.melonClientTabIcon ? "On" : "Off")));
        buttonList.add(new GuiButton(4, width / 2 - 155, height / 5 + 72 - 6, 150, 20, "Transp. InvBackground: " + (mc.options.melonBackgroundInventoryTransparent ? "On" : "Off")));
        buttonList.add(new GuiButton(5, width / 2 - 155, height / 5 + 96 - 6, 150, 20, "Transp. MenuBackground: " + (mc.options.melonBackgroundMenuTransparent ? "On" : "Off")));
        buttonList.add(new GuiButton(6, width / 2 - 155, height / 5 + 120 - 6, 150, 20, "BlockOverlay"));
        buttonList.add(new GuiButton(7, width / 2 - 155, height / 5 + 144 - 6, 150, 20, "Disable SlotScrolling: " + (mc.options.melonScrollSlotChangeDisabled ? "On" : "Off")));

        buttonList.add(new GuiButton(8, width / 2 + 5, height / 5 - 6, 150, 20, "User Interface"));
        buttonList.add(new GuiButton(9, width / 2 + 5, height / 5 + 24 - 6, 150, 20, "Discord Rich Presence"));
        buttonList.add(new GuiButton(10, width / 2 + 5, height / 5 + 48 - 6, 150, 20, "Video Settings Presets"));
        buttonList.add(new GuiButton(11, width / 2 + 5, height / 5 + 72 - 6, 150, 20, "Splash Text: " + (mc.options.melonSplashtextEnabled ? "On" : "Off")));
        buttonList.add(new GuiButton(12, width / 2 + 5, height / 5 + 96 - 6, 150, 20, "Custom Menu Panorama: " + (mc.options.melonCustompanorama ? "On" : "Off")));
        buttonList.add(new GuiButton(13, width / 2 + 5, height / 5 + 120 - 6, 150, 20, "Panorama Blur: " + (mc.options.melonPanoramablurEnabled ? "On" : "Off")));
        //buttonList.add(new GuiOptionSlider(13, width / 2 + 5, height / 5 + 120 - 6, GameSettings.Options.PANORAMABLUR, 0, 10));
        buttonList.add(new GuiButton(14, width / 2 + 5, height / 5 + 144 - 6, 150, 20, "Custom Sky: " + (mc.options.melonCustomSky ? "On" : "Off")));

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 5 + 168, LocalKeyTranslator.format("gui.done")));*/
    }

    /*** Called by the controls from the buttonList when activated. (Mouse pressed for buttons) */
    /*protected void actionPerformed(GuiButton button) {
        if (!button.enabled) return;
        switch (button.id) {
            case 200:
                mc.displayGuiScreen(this.parent);
                break;
            case 1:
                if (mc.options.melonNewTextures) System.out.println("New Textures ON");
                else System.out.println("New Textures OFF");
                mc.options.melonNewTextures = !mc.options.melonNewTextures;
                button.displayString = "Textures: " + (mc.options.melonNewTextures ? "New" : "Old");
                break;
            case 2:
                if (mc.options.melonFullbright) mc.options.saturation = 1.0f;
                else mc.options.saturation = 10.0f;
                mc.options.melonFullbright = !mc.options.melonFullbright;
                button.displayString = "FullBright: " + (mc.options.melonFullbright ? "On" : "Off");
                break;
            case 3:
                if (mc.options.melonClientTabIcon) System.out.println("TabIcon");
                else System.out.println("TabIcon");
                mc.options.melonClientTabIcon = !mc.options.melonClientTabIcon;
                button.displayString = "MelonClient Tab Icon: " + (mc.options.melonClientTabIcon ? "On" : "Off");
                break;
            case 4:
                mc.options.melonBackgroundInventoryTransparent = !mc.options.melonBackgroundInventoryTransparent;
                button.displayString = "Transp. InvBackground: " + (mc.options.melonBackgroundInventoryTransparent ? "On" : "Off");
                break;
            case 5:
                mc.options.melonBackgroundMenuTransparent = !mc.options.melonBackgroundMenuTransparent;
                button.displayString = "Transp. MenuBackground: " + (mc.options.melonBackgroundMenuTransparent ? "On" : "Off");
                break;
            case 6:
                //mc.displayGuiScreen();
                break;
            case 7:
                mc.options.melonScrollSlotChangeDisabled = !mc.options.melonScrollSlotChangeDisabled;
                button.displayString = "Disable SlotScrolling: " + (mc.options.melonScrollSlotChangeDisabled ? "On" : "Off");
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                mc.options.melonSplashtextEnabled = !mc.options.melonSplashtextEnabled;
                button.displayString = "Splash Text: " + (mc.options.melonSplashtextEnabled ? "On" : "Off");
                break;
            case 12:
                mc.options.melonCustompanorama = !mc.options.melonCustompanorama;
                button.displayString = "Custom Menu Panorama: " + (mc.options.melonCustompanorama ? "On" : "Off");
                break;
            case 13:
                //GuiOptionSlider slider = (GuiOptionSlider) button;
                //System.out.println(slider.displayString);
                //slider.mousePressed(mc,  mc.mouseHelper.deltaX, mc.mouseHelper.deltaY);
                mc.options.melonPanoramablurEnabled = !mc.options.melonPanoramablurEnabled;
                button.displayString = "Panorama Blur: " + (mc.options.melonPanoramablurEnabled ? "On" : "Off");
                break;
            case 14:
                if (mc.options.melonCustomSky) System.out.println("Custom Sky");
                else System.out.println("Custom Sky");
                mc.options.melonCustomSky = !mc.options.melonCustomSky;
                button.displayString = "Custom Sky: " + (mc.options.melonCustomSky ? "On" : "Off");
                break;
        }
        this.mc.options.saveOptions();
    }*/




    /*** Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks*/
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        super.render(stack, mouseX, mouseY, partialTicks);
        super.renderBackground(stack);
        font.draw(stack, this.title, this.width / 2, 15, 16777215);
        font.draw(stack, "Ingame:", this.width / 2 - 80, this.height / 9, 16777215);
        font.draw(stack, "Others:", this.width / 2 + 80, this.height / 9, 16777215);
  
    }

}
