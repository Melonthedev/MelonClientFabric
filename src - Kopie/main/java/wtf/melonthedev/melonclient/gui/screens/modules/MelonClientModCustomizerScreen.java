package wtf.melonthedev.melonclient.gui.screens.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.modhud.HudEditScreen;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;
import wtf.melonthedev.melonclient.mods.ModFlag;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;

public class MelonClientModCustomizerScreen extends MelonScreen {

    private final Screen parent;
    public String title;
    private ModuleDraggable[] renderers;
    private String warning = "";

    public MelonClientModCustomizerScreen(Screen parent, ModuleDraggable... renderers) {
        super(Component.literal("Mod Options"), true);
        this.parent = parent;
        this.renderers = renderers;
    }

    /*** Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the window resizes, the buttonList is cleared beforehand.*/
    @Override
    public void init()
    {
        this.title = "Mod Options";
        this.addRenderableWidget(new Button(width / 2 - 155, height / 5 - 6, 150, 20, Component.literal("Click to " + (renderers[0].isEnabled() ? "Disable" : "Enable")), (btn) -> {
            boolean isEnabled = renderers[0].isEnabled();
            for (ModuleDraggable renderer : renderers)
                renderer.setEnabled(!isEnabled);
            btn.setMessage(Component.literal("Click to " + (renderers[0].isEnabled() ? "Disable" : "Enable")));
        }));
        this.addRenderableWidget(new AbstractSliderButton(width / 2 - 155, height / 5 + 24 - 6, 150, 20, Component.literal("Scale: " + renderers[0].getOptions().scale), 0.5) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal("Scale: " + (double) Math.round((value / 100 * 100) * 10) / 10));
                for (ModuleDraggable renderer : renderers)  renderer.getOptions().scale = (float) Math.round((value / 100 * 100) * 10) / 10;
            }
            @Override
            protected void applyValue() {
                //TODO: Apply scale
            }
        });
        this.addRenderableWidget(new Button(width / 2 - 155, height / 5 + 48 - 6, 150, 20, Component.literal("Textcolor: " + renderers[0].getOptions().color + renderers[0].getOptions().color.getName().toUpperCase()), (btn) -> {
            Client.setScreen(new ColorSelectorScreen(this, renderers));
        }));
        boolean flag = renderers[0].getOptions().backgroundColor == null;
        this.addRenderableWidget(new Button(width / 2 - 155, height / 5 + 72 - 6, 150, 20, Component.literal("Background: " + (flag ? "None" : renderers[0].getOptions().backgroundColor.getName())), (btn) -> {
            Client.setScreen(new BackgroundSelectorScreen(this, renderers));
        }));
        this.addRenderableWidget(new Button(width / 2 - 155, height / 5 + 96 - 6, 150, 20, Component.literal("Show name of mod: " + (renderers[0].getOptions().showModName ? "True" : "False")), (btn) -> {
            for (ModuleDraggable renderer : renderers)  renderer.getOptions().showModName = !renderers[0].getOptions().showModName;
            btn.setMessage(Component.literal("Show name of mod: " + (renderers[0].getOptions().showModName ? "True" : "False")));
        }));
        if (renderers.length == 1) {
            int heightMultiplier = 0;
            for (ModFlag option : ModFlag.values()) {
                if (!option.getModOfFlag().getName().equals(renderers[0].getClass().getName())) continue;
                this.addRenderableWidget(new Button(width / 2 + 5, height / 5 - 6 + (24 * heightMultiplier), 150, 20, Component.literal(option.getTitle() + ": " + (renderers[0].getOptions().flags.contains(option) ? "On" : "Off")), (btn) -> {
                    boolean isNull = renderers[0].getOptions().flags.contains(option);
                    if (isNull) {
                        renderers[0].getOptions().flags.remove(option);
                    } else {
                        renderers[0].getOptions().flags.add(option);
                    }
                    renderers[0].saveOptionsToFile();
                    (btn).setMessage(Component.literal(option.getTitle() + ": " + (renderers[0].getOptions().flags.contains(option) ? "On" : "Off")));
                }));
                heightMultiplier++;
            }
        } else warning = "Too many mods selected";
        this.addRenderableWidget(new Button(width / 2 - 100, height / 5 + 168, 200, 20, Component.translatable("gui.done"), (btn) -> {
            for (ModuleDraggable renderer : renderers)  renderer.updateDummy();
            Client.setScreen(parent);
        }));
    }

    /*** Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks*/
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        if (parent instanceof HudEditScreen) {
            ((HudEditScreen) parent).renderDummies(stack);
            for (ModuleDraggable renderer : renderers) ((HudEditScreen) parent).renderSelectedForRenderer(stack, renderer);
        }
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 16777215);
        drawCenteredString(stack, this.font, "General:", this.width / 2 - 80, this.height / 9, 16777215);
        drawCenteredString(stack, this.font, "Mod Specific:", this.width / 2 + 80, this.height / 9, 16777215);
        drawCenteredString(stack, this.font,  Component.literal(warning).withStyle(ChatFormatting.ITALIC), this.width / 2 + 80, this.height / 9 + 80, 16777215);
  
    }
}
