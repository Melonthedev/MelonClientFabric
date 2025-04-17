package wtf.melonthedev.melonclient.gui.modhud;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.GuiUtils;
import wtf.melonthedev.melonclient.gui.screens.modules.MelonClientModCustomizerScreen;
import wtf.melonthedev.melonclient.gui.screens.modules.ModSelectionList;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;
import wtf.melonthedev.melonclient.mods.ModuleDraggable;
import wtf.melonthedev.melonclient.utils.BackgroundColor;

import java.awt.Color;
import java.util.List;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HudEditScreen extends MelonScreen {

    private final Minecraft mc = Client.getMinecraft();
    private final HashMap<IRenderer, ScreenPosition> rendererPositions = new HashMap<>();
    private final List<IRenderer> selectedRenderers = new ArrayList<>();

    private int prevX, prevY;
    private int x, y;
    private boolean shouldDrawSelection;
    private boolean isLeftMouseDown = false;
    private boolean selectedMultiple = false;

    private Button modsButton;
    private EditBox modSearchBox;
    private boolean isModSearcherOpen = false;
    private ModSelectionList list;
    private List<Button> modToggleButtons;
    private List<Button> modEditButtons;

    public HudEditScreen(HudManager hudManager) {
        super(Component.literal("HUD Editor"));
        Collection<IRenderer> registeredRenderers = hudManager.getRegisterdRenderers();
        for (IRenderer ren : registeredRenderers) {
            ScreenPosition pos = ren.load().position;
            if (pos == null) pos = ScreenPosition.fromRelativePosition(5, 5);
            adjustBounds(ren, pos);
            this.rendererPositions.put(ren, pos);
        }
        shouldDrawTitleString(false);
    }

    public void tick() {
        this.modSearchBox.tick();
    }

    @Override
    public void init() {
        modsButton = this.addRenderableWidget(new Button(Component.literal("Mods"), (button) -> {
            if (this.modsButton.y == height / 2 - 10) openModSearcher();
            else closeModSearcher();
        }).bounds(width / 2 - 25, height / 2 - 10, 50, 20).build());
        modSearchBox = this.addRenderableWidget(new EditBox(this.font, width / 2 - 100, height / 6 + 30, 200, 20, Component.literal("Search For Mods...")));
        modSearchBox.setVisible(false);
        modSearchBox.setResponder(s -> list.refreshList(s));
        list = new ModSelectionList(this, minecraft, 220, height / 6 * 5 - 60, height / 6 + 60, height, 36, () -> this.modSearchBox.getValue(), this.list);
        list.setRenderBackground(false);
        list.setRenderTopAndBottom(false);
        list.setLeftPos(width/ 2 - 110);
        for (ModSelectionList.Entry entry : list.children()) {
            if (!(entry instanceof ModSelectionList.ModListEntry modListEntry)) continue;


        }
        /*modAddButton = this.addRenderableWidget(new Button(width / 2 + 100 - 36, height / 6 + 60 + 6, 30, 20, Component.literal("Add"), (button) -> {

        }));
        modAddButton.visible = false;
        modEditButton = this.addRenderableWidget(new Button(width / 2 + 100 - 36 - 36, height / 6 + 60 + 6, 30, 20, Component.literal("Edit"), (button) -> {

        }));
        modEditButton.visible = false;*/
        this.setInitialFocus(this.modSearchBox);
        if (isModSearcherOpen) openModSearcher();
        else closeModSearcher();
    }

    public void openModSearcher() {
        this.modsButton.y = height / 6;
        this.modSearchBox.setVisible(true);
        this.modSearchBox.setFocus(true);
        this.isModSearcherOpen = true;
        addRenderableWidget(list);
        //modEditButton.visible = true;
        //modAddButton.visible = true;
    }

    public void closeModSearcher() {
        this.modsButton.y = height / 2 - 10;
        this.modSearchBox.setVisible(false);
        this.modSearchBox.setFocus(false);
        this.isModSearcherOpen = false;
        removeWidget(list);
        //modEditButton.visible = false;
        //modAddButton.visible = false;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(stack);
        final int blitOffsetOld = this.getBlitOffset();
        if (isModSearcherOpen) {
            //list.render(stack, mouseX, mouseY, partialTicks);
            //modSearchBox.render(stack, mouseX, mouseY, partialTicks);
        }
        setBlitOffset(200);
        super.render(stack, mouseX, mouseY, partialTicks);
        setBlitOffset(300);
        renderDummies(stack);
        drawHollowRect(stack, 0, 0, this.width - 1, this.height - 1, 0xFF00FFFF);
        drawSelectedModsIndicator(stack);
        if (shouldDrawSelection) drawSelectionField(stack);
        setBlitOffset(blitOffsetOld);
    }

    public void renderDummies(PoseStack stack) {
        for (IRenderer renderer : rendererPositions.keySet()) {
            ScreenPosition pos = rendererPositions.get(renderer);
            renderer.renderDummy(pos, GuiUtils.stack);
            if (renderer.isEnabled())
                this.drawHollowRect(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY() - 2, renderer.getWidth() + 4, renderer.getHeight() + 3, BackgroundColor.GRAY_NORMAL.getColor());
            else this.drawHollowRect(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY() - 2, renderer.getWidth() + 4, renderer.getHeight() + 3, new Color(220, 0, 0, 50).getRGB());
        }
    }

    public void renderSelectedForRenderer(PoseStack stack, IRenderer renderer) {
        if (renderer == null) return;
        ScreenPosition pos = rendererPositions.get(renderer);
        this.drawHollowRect(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY() - 2, renderer.getWidth() + 4, renderer.getHeight() + 3, Color.CYAN.getRGB());
    }

    private void adjustBounds(IRenderer ren, ScreenPosition pos) {
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - ren.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - ren.getHeight(), 0)));
        pos.setAbsolute(absoluteX, absoluteY);
    }

    public void drawHollowRect(PoseStack stack, int x, int y, int width, int height, int color) {
        this.hLine(stack, x, x + width, y, color);
        this.hLine(stack, x, x + width, y + height, color);
        this.vLine(stack, x, y + height, y, color);
        this.vLine(stack, x + width, y, y + height, color);
    }

    private void drawSelectionField(PoseStack stack) {
        if (isLeftMouseDown) {
            drawHollowRect(stack, prevX, prevY, x - prevX, y - prevY, BackgroundColor.GRAY_NORMAL.getColor());
            for (IRenderer renderer : rendererPositions.keySet().stream().filter(new SelectionOverFinder(x, y, prevX, prevY)).collect(Collectors.toList())) {
                ScreenPosition pos = rendererPositions.get(renderer);
                this.drawHollowRect(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY() - 2, renderer.getWidth() + 4, renderer.getHeight() + 3, 0xF00000FF);
            }
        } else {
            shouldDrawSelection = false;
            for (IRenderer renderer : rendererPositions.keySet().stream().filter(new SelectionOverFinder(x, y, prevX, prevY)).collect(Collectors.toList())) {
                if (!selectedRenderers.contains(renderer)) selectedRenderers.add(renderer);
                selectedMultiple = true;
            }
        }
    }

    private void drawSelectedModsIndicator(PoseStack stack) {
        for (IRenderer renderer : selectedRenderers) {
            ScreenPosition pos = rendererPositions.get(renderer);
            this.drawHollowRect(stack, pos.getAbsoluteX() - 2, pos.getAbsoluteY() - 2, renderer.getWidth() + 4, renderer.getHeight() + 3, Color.CYAN.getRGB());
        }
    }


    private void loadMouseOver(int x, int y) {
        if (isOverMod(x, y) && selectedMultiple)
            return;
        selectedMultiple = false;
        selectedRenderers.clear();
        selectedRenderers.addAll(rendererPositions.keySet().stream().filter(new MouseOverFinder(x, y)).collect(Collectors.toList()));
    }

    private boolean isOverMod(int x, int y) {
        for (IRenderer renderer : rendererPositions.keySet())
            if (new MouseOverFinder(x, y).test(renderer))
                return true;
        return false;
    }

    private List<ModuleDraggable> getOverMods(int x, int y) {
        List<ModuleDraggable> mods = new ArrayList<>();
        for (IRenderer renderer : rendererPositions.keySet())
            if (new MouseOverFinder(x, y).test(renderer))
                mods.add((ModuleDraggable) renderer);
        //return (ModuleDraggable) renderer;
        return mods;
    }

    private void moveSelectedRenderersBy(int x, int y) {
        for (IRenderer renderer : selectedRenderers)
            moveRendererBy(renderer,  x, y);
    }

    private void moveRendererBy(IRenderer renderer, int x, int y) {
        ScreenPosition pos = rendererPositions.get(renderer);
        pos.setAbsolute(pos.getAbsoluteX() + x , pos.getAbsoluteY() + y);
        adjustBounds(renderer, pos);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        this.shouldDrawSelection = false;
        this.prevX = (int) x;
        this.prevY = (int) y;
        loadMouseOver(prevX, prevY);
        if (button == 0) {
            if (selectedRenderers.size() > 0 && isOverMod((int) x, (int) y) && getOverMods((int) x, (int) y).stream().noneMatch(selectedRenderers::contains)) {
                selectedMultiple = false;
                selectedRenderers.clear();
            }
            isLeftMouseDown = true;
        }
        if (button == 1) {
            ModuleDraggable[] mods = selectedRenderers.toArray(ModuleDraggable[]::new);
            if (selectedRenderers.size() > 1) Client.setScreen(new MelonClientModCustomizerScreen(this, mods));
            else if (selectedRenderers.size() > 0) Client.setScreen(new MelonClientModCustomizerScreen(this, (ModuleDraggable) selectedRenderers.get(0)));
            else return super.mouseClicked(x, y, button);
            isLeftMouseDown = false;
        }
        return super.mouseClicked(x, y, button);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int button) {
        if (button == 0) isLeftMouseDown = false;
        return super.mouseReleased(p_94722_, p_94723_, button);
    }

    @Override
    public boolean mouseDragged(double x, double y, int button, double p_94702_, double p_94703_) {
        if (selectedRenderers.size() > 0 && isLeftMouseDown) {
            moveSelectedRenderersBy((int) x - prevX, (int) y - prevY);
            shouldDrawSelection = false;
            this.prevX = (int) x;
            this.prevY = (int) y;
        } else {
            shouldDrawSelection = true;
            this.x = (int) x;
            this.y = (int) y;
        }
        return super.mouseDragged(x, y, button, p_94702_, p_94703_);
    }

    @Override
    public boolean keyPressed(int keycode, int p_96553_, int p_96554_) {
        switch (keycode) {
            case InputConstants.KEY_ESCAPE -> {
                rendererPositions.forEach((renderer, pos) -> {
                    renderer.load().position = pos;
                    renderer.save(renderer.load());
                });
                Client.setScreen(null);
            }
            case InputConstants.KEY_UP -> moveSelectedRenderersBy(0, -1);
            case InputConstants.KEY_DOWN -> moveSelectedRenderersBy(0, 1);
            case InputConstants.KEY_LEFT -> moveSelectedRenderersBy(-1, 0);
            case InputConstants.KEY_RIGHT -> moveSelectedRenderersBy(1, 0);
        }
        return super.keyPressed(keycode, p_96553_, p_96554_);
    }

    @Override
    public void onClose() {
        rendererPositions.forEach((renderer, pos) -> {
            renderer.load().position = pos;
            renderer.save(renderer.load());
        });
    }

    private class MouseOverFinder implements Predicate<IRenderer> {

        private final int mouseX, mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }
        @Override
        public boolean test(IRenderer renderer) {
            ScreenPosition pos = rendererPositions.get(renderer);
            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();
            return mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()
                    && mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight();
        }
    }

    private class SelectionOverFinder implements Predicate<IRenderer> {

        private final int x, y, xEnd, yEnd;

        public SelectionOverFinder(int x, int y, int xEnd, int yEnd) {
            this.x = x;
            this.y = y;
            this.xEnd = xEnd;
            this.yEnd = yEnd;
        }
        @Override
        public boolean test(IRenderer renderer) {
            ScreenPosition pos = rendererPositions.get(renderer);
            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();
            return     (absoluteX >= x && absoluteX <= xEnd
                    && absoluteY >= y && absoluteY <= yEnd)
                    || (absoluteX <= x && absoluteX >= xEnd
                    && absoluteY <= y && absoluteY >= yEnd)
                    || (absoluteX >= x && absoluteX <= xEnd
                    && absoluteY <= y && absoluteY >= yEnd)
                    || (absoluteX <= x && absoluteX >= xEnd
                    && absoluteY >= y && absoluteY <= yEnd) 
                    ||
                    (absoluteX + renderer.getWidth() >= x && absoluteX + renderer.getWidth() <= xEnd
                            && absoluteY + renderer.getHeight() >= y && absoluteY + renderer.getHeight() <= yEnd)
                    || (absoluteX + renderer.getWidth() <= x && absoluteX + renderer.getWidth() >= xEnd
                    && absoluteY + renderer.getHeight() <= y && absoluteY + renderer.getHeight() >= yEnd)
                    || (absoluteX + renderer.getWidth() >= x && absoluteX + renderer.getWidth() <= xEnd
                    && absoluteY + renderer.getHeight() <= y && absoluteY + renderer.getHeight() >= yEnd)
                    || (absoluteX + renderer.getWidth() <= x && absoluteX + renderer.getWidth() >= xEnd
                    && absoluteY + renderer.getHeight() >= y && absoluteY + renderer.getHeight() <= yEnd)
                    ||
                    (absoluteX + renderer.getWidth() >= x && absoluteX + renderer.getWidth() <= xEnd
                            && absoluteY  >= y && absoluteY  <= yEnd)
                    || (absoluteX + renderer.getWidth() <= x && absoluteX + renderer.getWidth() >= xEnd
                    && absoluteY  <= y && absoluteY  >= yEnd)
                    || (absoluteX + renderer.getWidth() >= x && absoluteX + renderer.getWidth() <= xEnd
                    && absoluteY  <= y && absoluteY  >= yEnd)
                    || (absoluteX + renderer.getWidth() <= x && absoluteX + renderer.getWidth() >= xEnd
                    && absoluteY  >= y && absoluteY  <= yEnd)
                    ||
                    (absoluteX >= x && absoluteX <= xEnd
                            && absoluteY + renderer.getHeight() >= y && absoluteY + renderer.getHeight() <= yEnd)
                    || (absoluteX <= x && absoluteX >= xEnd
                    && absoluteY + renderer.getHeight() <= y && absoluteY + renderer.getHeight() >= yEnd)
                    || (absoluteX >= x && absoluteX <= xEnd
                    && absoluteY + renderer.getHeight() <= y && absoluteY + renderer.getHeight() >= yEnd)
                    || (absoluteX <= x && absoluteX >= xEnd
                    && absoluteY + renderer.getHeight() >= y && absoluteY + renderer.getHeight() <= yEnd);
        }
    }
}
