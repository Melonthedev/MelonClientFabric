package wtf.melonthedev.melonclient.modengine.rendering;

import wtf.melonthedev.melonclient.modengine.hud.ModDraggableOptions;

public interface IRendererConfig {
    void save(ModDraggableOptions options);

    ModDraggableOptions load();
}
