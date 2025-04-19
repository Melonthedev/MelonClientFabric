package wtf.melonthedev.melonclient.gui.modengine;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;
import wtf.melonthedev.melonclient.modengine.ModFlag;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;

public class MelonClientModCustomizerScreen extends MelonScreen {

    private final Screen parent;
    public String title;
    private ModDraggable[] renderers;
    private String warning = "";

    public MelonClientModCustomizerScreen(Screen parent, ModDraggable... renderers) {
        super(Component.literal("Mod Options"), true);
        this.parent = parent;
        this.renderers = renderers;
    }

    /*** Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the window resizes, the buttonList is cleared beforehand.*/
    @Override
    public void init()
    {
        this.title = "Mod Options";
        this.addRenderableWidget(Button.builder(Component.literal("Click to " + (renderers[0].isEnabled() ? "Disable" : "Enable")), (btn) -> {
            boolean isEnabled = renderers[0].isEnabled();
            for (ModDraggable renderer : renderers)
                renderer.setEnabled(!isEnabled);
            btn.setMessage(Component.literal("Click to " + (renderers[0].isEnabled() ? "Disable" : "Enable")));
        }).bounds(width / 2 - 155, height / 5 - 6, 150, 20).build());
        this.addRenderableWidget(new AbstractSliderButton(width / 2 - 155, height / 5 + 24 - 6, 150, 20, Component.literal("Scale: " + renderers[0].getOptions().scale), 0.5) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal("Scale: " + (double) Math.round((value / 100 * 100) * 10) / 10));
                for (ModDraggable renderer : renderers)  renderer.getOptions().scale = (float) Math.round((value / 100 * 100) * 10) / 10;
            }
            @Override
            protected void applyValue() {
                //TODO: Apply scale
            }
        });
        this.addRenderableWidget(Button.builder(Component.literal("Textcolor: " + renderers[0].getOptions().color + renderers[0].getOptions().color.getName().toUpperCase()), (btn) -> {
            Client.setScreen(new ColorSelectorScreen(this, renderers));
        }).bounds(width / 2 - 155, height / 5 + 48 - 6, 150, 20).build());
        boolean flag = renderers[0].getOptions().backgroundColor == null;
        this.addRenderableWidget(Button.builder(Component.literal("Background: " + (flag ? "None" : renderers[0].getOptions().backgroundColor.getName())), (btn) -> {
            Client.setScreen(new BackgroundSelectorScreen(this, renderers));
        }).bounds(width / 2 - 155, height / 5 + 72 - 6, 150, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Show name of mod: " + (renderers[0].getOptions().showModName ? "True" : "False")), (btn) -> {
            for (ModDraggable renderer : renderers)  renderer.getOptions().showModName = !renderers[0].getOptions().showModName;
            btn.setMessage(Component.literal("Show name of mod: " + (renderers[0].getOptions().showModName ? "True" : "False")));
        }).bounds(width / 2 - 155, height / 5 + 96 - 6, 150, 20).build());
        if (renderers.length == 1) {
            int heightMultiplier = 0;
            for (ModFlag option : ModFlag.values()) {
                if (!option.getModOfFlag().getName().equals(renderers[0].getClass().getName())) continue;
                this.addRenderableWidget(Button.builder(Component.literal(option.getTitle() + ": " + (renderers[0].getOptions().flags.contains(option) ? "On" : "Off")), (btn) -> {
                    boolean isNull = renderers[0].getOptions().flags.contains(option);
                    if (isNull) {
                        renderers[0].getOptions().flags.remove(option);
                    } else {
                        renderers[0].getOptions().flags.add(option);
                    }
                    renderers[0].saveOptionsToFile();
                    (btn).setMessage(Component.literal(option.getTitle() + ": " + (renderers[0].getOptions().flags.contains(option) ? "On" : "Off")));
                }).bounds(width / 2 + 5, height / 5 - 6 + (24 * heightMultiplier), 150, 20).build());
                heightMultiplier++;
            }
        } else warning = "Too many mods selected";
        this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), (btn) -> {
            for (ModDraggable renderer : renderers)  renderer.updateDummy();
            Client.setScreen(parent);
        }).bounds(width / 2 - 100, height / 5 + 168, 200, 20).build());
    }

    @Override
    public void onClose() {
        Client.setScreen(parent);
    }

    /*** Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks*/
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        if (parent instanceof HudEditScreen) {
            ((HudEditScreen) parent).renderDummies(guiGraphics);
            for (ModDraggable renderer : renderers) ((HudEditScreen) parent).renderSelectedForRenderer(guiGraphics, renderer);
        }
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        guiGraphics.drawCenteredString(this.font, "General:", this.width / 2 - 80, this.height / 9, 16777215);
        guiGraphics.drawCenteredString(this.font, "Mod Specific:", this.width / 2 + 80, this.height / 9, 16777215);
        guiGraphics.drawCenteredString(this.font,  Component.literal(warning).withStyle(ChatFormatting.ITALIC), this.width / 2 + 80, this.height / 9 + 80, 16777215);
  
    }
}
