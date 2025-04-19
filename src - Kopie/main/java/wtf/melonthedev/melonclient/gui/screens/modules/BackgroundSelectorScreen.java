package wtf.melonthedev.melonclient.gui.screens.modengine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;
import wtf.melonthedev.melonclient.utils.BackgroundColor;

public class BackgroundSelectorScreen extends MelonScreen {

    private Screen parent;
    private String title;
    private ModuleDraggable[] mods;

    protected BackgroundSelectorScreen(Screen parent, ModuleDraggable... mods) {
        super(Component.literal("Choose a color"), true);
        this.parent = parent;
        this.mods = mods;
    }

    @Override
    public void init() {
        title = "Choose a background color";
        addColorButtons();
    }

    @Override
    public void onClose() {
        super.onClose();
        Client.setScreen(parent);
    }

    private void addColorButtons() {
        addRenderableWidget(new Button(width / 2 - 155, height / 6 + 48  - 6, 150, 20, Component.literal("None"), button -> handleClick(null)));
        addRenderableWidget(new Button(width / 2 - 155, height / 6 + 72  - 6, 150, 20, Component.literal(BackgroundColor.WHITE_LIGHT.getName()), button -> handleClick(BackgroundColor.WHITE_LIGHT)));
        addRenderableWidget(new Button(width / 2 - 155, height / 6 + 96  - 6, 150, 20, Component.literal(BackgroundColor.WHITE_NORMAL.getName()), button -> handleClick(BackgroundColor.WHITE_NORMAL)));
        addRenderableWidget(new Button(width / 2 - 155, height / 6 + 120 - 6, 150, 20, Component.literal(BackgroundColor.WHITE_TRANSLUCENT.getName()), button -> handleClick(BackgroundColor.WHITE_TRANSLUCENT)));
        addRenderableWidget(new Button(width / 2 - 155, height / 6 + 144 - 6, 150, 20, Component.literal(BackgroundColor.GRAY_LIGHT.getName()), button -> handleClick(BackgroundColor.GRAY_LIGHT)));
        addRenderableWidget(new Button(width / 2 + 5, height / 6 + 48 - 6, 150, 20, Component.literal(BackgroundColor.GRAY_NORMAL.getName()), button -> handleClick(BackgroundColor.GRAY_NORMAL)));
        addRenderableWidget(new Button(width / 2 + 5, height / 6 + 72 - 6, 150, 20, Component.literal(BackgroundColor.GRAY_TRANSLUCENT.getName()), button -> handleClick(BackgroundColor.GRAY_TRANSLUCENT)));
        addRenderableWidget(new Button(width / 2 + 5, height / 6 + 96 - 6, 150, 20, Component.literal(BackgroundColor.BLACK_LIGHT.getName()), button -> handleClick(BackgroundColor.BLACK_LIGHT)));
        addRenderableWidget(new Button(width / 2 + 5, height / 6 + 120 - 6, 150, 20, Component.literal(BackgroundColor.BLACK_NORMAL.getName()), button -> handleClick(BackgroundColor.BLACK_NORMAL)));
        addRenderableWidget(new Button(width / 2 + 5, height / 6 + 144 - 6, 150, 20, Component.literal(BackgroundColor.BLACK_TRANSLUCENT.getName()), button -> handleClick(BackgroundColor.BLACK_TRANSLUCENT)));
    }

    private void handleClick(BackgroundColor color) {
        for (ModuleDraggable mod : mods)
            mod.getOptions().backgroundColor = color;
        Client.setScreen(parent);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float ticks) {
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, ticks);
        drawCenteredString(stack, font, title, width / 2, height / 6, 0xFFFFFF);
    }
}