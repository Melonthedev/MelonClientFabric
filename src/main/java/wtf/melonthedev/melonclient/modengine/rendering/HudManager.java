package wtf.melonthedev.melonclient.modengine.rendering;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.event.EventManager;
import wtf.melonthedev.melonclient.gui.modengine.HudEditScreen;
import wtf.melonthedev.melonclient.gui.modengine.MelonClientModCustomizerScreen;
import wtf.melonthedev.melonclient.modengine.ModInstanceManager;
import wtf.melonthedev.melonclient.modengine.hud.ModDraggable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class HudManager {
    private final Set<IRenderer> registeredRenderers = Sets.newHashSet();
    private final Minecraft mc = Client.getMinecraft();
    private static HudManager INSTANCE;

    private HudManager() {}

    public static HudManager getINSTANCE() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new HudManager();
        EventManager.register(INSTANCE);
        return INSTANCE;
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
        Client.setScreen(new HudEditScreen(this));
    }

    public void onRender(GuiGraphics guiGraphics) {
        if ((mc.hasSingleplayerServer() || mc.getCurrentServer() != null) && !(mc.screen instanceof HudEditScreen)  && !(mc.screen instanceof MelonClientModCustomizerScreen)) {
            for (ModDraggable moduleDraggable : ModInstanceManager.getMods()) {
                if (!moduleDraggable.isEnabled()) continue;
                ScreenPosition pos = moduleDraggable.getPosition();
                if (pos == null) pos = ScreenPosition.fromRelativePosition(5,  5);
                moduleDraggable.render(pos, guiGraphics);
            }
        }
    }

}
