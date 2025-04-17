package wtf.melonthedev.melonclient.gui.screens.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

public class ColorSelectorScreen extends MelonScreen {

    private Screen parent;
    private String title;
    private ModuleDraggable[] mods;

    protected ColorSelectorScreen(Screen parent, ModuleDraggable... mods) {
        super(Component.literal("Choose a color"), true);
        this.parent = parent;
        this.mods = mods;
    }

    @Override
    public void init() {
        title = "Choose a color";
        addColorButtons();
    }

    @Override
    public void onClose() {
        super.onClose();
        Client.setScreen(parent);
    }

    private void addColorButtons() {
        /*addRenderableWidget(Button.builder(width / 2 - 85, height / 2 - 75, 80, 20, Component.literal(ChatFormatting.AQUA.getName().toUpperCase()).withStyle(ChatFormatting.AQUA), button -> {
            handleClick(ChatFormatting.AQUA);
        }));
        addRenderableWidget(Button.builder(width / 2 - 85, height / 2 - 50, 80, 20, Component.literal(ChatFormatting.RED.getName().toUpperCase()).withStyle(ChatFormatting.RED), button -> {
            handleClick(ChatFormatting.RED);
        }));
        addRenderableWidget(Button.builder(width / 2 - 85, height / 2 - 25, 80, 20, Component.literal(ChatFormatting.BLACK.getName().toUpperCase()).withStyle(ChatFormatting.BLACK), button -> {
            handleClick(ChatFormatting.BLACK);
        }));
        addRenderableWidget(Button.builder(width / 2 - 85, height / 2, 80, 20, Component.literal(ChatFormatting.WHITE.getName().toUpperCase()).withStyle(ChatFormatting.WHITE), button -> {
            handleClick(ChatFormatting.WHITE);
        }));
        addRenderableWidget(Button.builder(width / 2 - 85, height / 2 + 25, 80, 20, Component.literal(ChatFormatting.GOLD.getName().toUpperCase()).withStyle(ChatFormatting.GOLD), button -> {
            handleClick(ChatFormatting.GOLD);
        }));
        addRenderableWidget(Button.builder(width / 2 - 85, height / 2 + 50, 80, 20, Component.literal(ChatFormatting.GRAY.getName().toUpperCase()).withStyle(ChatFormatting.GRAY), button -> {
            handleClick(ChatFormatting.GRAY);
        }));
        addRenderableWidget(Button.builder(width / 2 - 85, height / 2 + 75, 80, 20, Component.literal(ChatFormatting.GREEN.getName().toUpperCase()).withStyle(ChatFormatting.GREEN), button -> {
            handleClick(ChatFormatting.GREEN);
        }));
        /*addRenderableWidget(Button.builder(width / 2 - 85, height / 2 + 75, 80, 20, Component.literal(ChatFormatting.WHITE.getName().toUpperCase()).withStyle(ChatFormatting.WHITE), button -> {
            handleClick(ChatFormatting.WHITE);
        }));*//*

        addRenderableWidget(Button.builder(width / 2 + 5, height / 2 - 75, 80, 20, Component.literal(ChatFormatting.BLUE.getName().toUpperCase()).withStyle(ChatFormatting.BLUE), button -> {
            handleClick(ChatFormatting.BLUE);
        }));
        addRenderableWidget(Button.builder(width / 2 + 5, height / 2 - 50, 80, 20, Component.literal(ChatFormatting.DARK_AQUA.getName().toUpperCase()).withStyle(ChatFormatting.DARK_AQUA), button -> {
            handleClick(ChatFormatting.DARK_AQUA);
        }));
        addRenderableWidget(Button.builder(width / 2 + 5, height / 2 - 25, 80, 20, Component.literal(ChatFormatting.DARK_BLUE.getName().toUpperCase()).withStyle(ChatFormatting.DARK_BLUE), button -> {
            handleClick(ChatFormatting.DARK_BLUE);
        }));
        addRenderableWidget(Button.builder(width / 2 + 5, height / 2, 80, 20, Component.literal(ChatFormatting.DARK_GRAY.getName().toUpperCase()).withStyle(ChatFormatting.DARK_GRAY), button -> {
            handleClick(ChatFormatting.DARK_GRAY);
        }));
        addRenderableWidget(Button.builder(width / 2 + 5, height / 2 + 25, 80, 20, Component.literal(ChatFormatting.DARK_GREEN.getName().toUpperCase()).withStyle(ChatFormatting.DARK_GREEN), button -> {
            handleClick(ChatFormatting.DARK_GREEN);
        }));
        addRenderableWidget(Button.builder(width / 2 + 5, height / 2 + 50, 80, 20, Component.literal(ChatFormatting.DARK_PURPLE.getName().toUpperCase()).withStyle(ChatFormatting.DARK_PURPLE), button -> {
            handleClick(ChatFormatting.DARK_PURPLE);
        }));
        addRenderableWidget(Button.builder(width / 2 + 5, height / 2 + 75, 80, 20, Component.literal(ChatFormatting.LIGHT_PURPLE.getName().toUpperCase()).withStyle(ChatFormatting.LIGHT_PURPLE), button -> {
            handleClick(ChatFormatting.LIGHT_PURPLE);
        }));*/
    }

    private void handleClick(ChatFormatting color) {
        for (ModuleDraggable mod : mods)
            mod.getOptions().color = color;
        Client.setScreen(parent);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        super.renderBackground(guiGraphics, mouseX, mouseY, ticks);
        super.render(guiGraphics, mouseX, mouseY, ticks);
        guiGraphics.drawCenteredString(font, title, width / 2, height / 2 - 100, 0xFFFFFF);
    }
}
