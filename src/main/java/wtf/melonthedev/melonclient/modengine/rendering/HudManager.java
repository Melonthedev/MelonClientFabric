package wtf.melonthedev.melonclient.modengine.rendering;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.event.EventManager;
import wtf.melonthedev.melonclient.gui.screens.modengine.HudEditScreen;
import wtf.melonthedev.melonclient.gui.screens.modengine.MelonClientModCustomizerScreen;
import wtf.melonthedev.melonclient.modengine.ModInstanceManager;
import wtf.melonthedev.melonclient.modengine.ModuleDraggable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class HudManager {
    private final Set<IRenderer> registeredRenderers = Sets.newHashSet();
    private final Minecraft mc = Client.getMinecraft();
    private static HudManager INSTANCE;
    private static HudEditScreen screen;

    private HudManager() {}

    public static HudManager getINSTANCE() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new HudManager();
        EventManager.register(INSTANCE);
        screen = new HudEditScreen(INSTANCE);
        return INSTANCE;
    }

    public HudEditScreen getConfigScreen() {
        return screen;
    }

    public void register(IRenderer...renderers) {
        this.registeredRenderers.addAll(Arrays.asList(renderers));
    }

    public void unregister(IRenderer...renderers) {
        Arrays.asList(renderers).forEach(this.registeredRenderers::remove);
    }

    public Collection<IRenderer> getRegisterdRenderers() {
        return Sets.newHashSet(registeredRenderers);
    }

    public void openConfigScreen() {
        screen = new HudEditScreen(this);
        Client.setScreen(screen);
        //Client.setScreen(getConfigScreen());
    }

    public void onRender(GuiGraphics guiGraphics) {
        if ((mc.hasSingleplayerServer() || mc.getCurrentServer() != null) && !(mc.screen instanceof HudEditScreen)  && !(mc.screen instanceof MelonClientModCustomizerScreen)) {
            for (ModuleDraggable moduleDraggable : ModInstanceManager.getMods()) {
                //if (mc.options.renderDebugCharts && moduleDraggable.load().position.getAbsoluteY() <= 130) continue;
                if (!moduleDraggable.isEnabled()) continue;
                ScreenPosition pos = moduleDraggable.load().position;
                if (pos == null) pos = ScreenPosition.fromRelativePosition(5,  5);
                moduleDraggable.render(pos, guiGraphics);
            }
        }
    }

    /*private void callRenderer(IRenderer renderer) {
        if (!renderer.isEnabled()) return;
        ScreenPosition pos = renderer.load().position;
        if (pos == null) pos = ScreenPosition.fromRelativePosition(5,  5);
        renderer.render(pos, GuiUtils.stack, GuiUtils.guiGraphics);
    }*/

}
