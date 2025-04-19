package wtf.melonthedev.melonclient.modengine.rendering;

import wtf.melonthedev.melonclient.modengine.ModOptions;

public interface IRendererConfig {
    void save(ModOptions options);

    ModOptions load();
}
