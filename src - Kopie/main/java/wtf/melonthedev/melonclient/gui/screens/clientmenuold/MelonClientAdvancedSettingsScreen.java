package wtf.melonthedev.melonclient.gui.screens.clientmenuold;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.ApiWrapper;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

import java.awt.*;

public class MelonClientAdvancedSettingsScreen extends MelonScreen {

    private EditBox customApiUrlBox;

    public MelonClientAdvancedSettingsScreen(Screen parent) {
        super(Component.literal("Advanced Options"), true);
        this.parent = parent;
    }

    @Override
    public void init()
    {
        /*customApiUrlBox = new EditBox(this.font, this.width / 2 - 155 + 2, this.height / 6 + 60, 215, 20, this.customApiUrlBox, Component.literal("Custom Api Url..."));
        //if (!mc.options.customApiUrl.equals("")) customApiUrlBox.setValue(mc.options.customApiUrl);
        addRenderableWidget(new Button(this.width / 2 - 155, this.height / 6 + 15, 150, 20, Component.literal("Developer Mode: " + (mc.options.enableDeveloperMode.get() ? "On" : "Off")), (button) -> {
            mc.options.enableDeveloperMode.set(!mc.options.enableDeveloperMode.get());
            mc.setScreen(new MelonClientAdvancedSettingsScreen(parent));
        }));
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 6 + 160, 204, 20, Component.translatable("gui.done"), (button) ->
                mc.setScreen(parent)));
        if (!mc.options.enableDeveloperMode.get()) {
            return;
        }
        addRenderableWidget(new Button(this.width / 2 + 5, this.height / 6 + 15, 150, 20, Component.literal("GitHub Repository"), (button) -> {
            Util.getPlatform().openUri("https://github.com/Melonthedev/MelonClient1.19");
            Util.getPlatform().openUri("https://github.com/Melonthedev/MelonClient");
        }));
        addRenderableWidget(customApiUrlBox);
        addRenderableWidget(new Button(this.width / 2 + 65 + 4, this.height / 6 + 60, 40, 20, Component.literal("Set"), (button) -> {
            mc.options.customApiUrl = customApiUrlBox.getValue();
            ApiWrapper.URL = customApiUrlBox.getValue();
        }));
        addRenderableWidget(new Button(this.width / 2 + 110 + 4, this.height / 6 + 60, 40, 20, Component.literal("Reset"), (button) -> {
            this.customApiUrlBox.setValue("");
            mc.options.customApiUrl = "";
            ApiWrapper.URL = Client.ApiUrl;
        }));*/

    }

    @Override
    public void onClose() {
        super.onClose();
        //mc.options.save();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 16777215);
        drawCenteredString(stack, this.font, "Note: Only modify options, if you know what you are doing!", this.width / 2, 30, Color.GRAY.getRGB());
        //if (mc.options.enableDeveloperMode.get()) {
        //    drawCenteredString(stack, this.font, "Custom Api Url:", this.width / 2, this.height / 6 + 45, 16777215);
            //...
        //}

    }
}
