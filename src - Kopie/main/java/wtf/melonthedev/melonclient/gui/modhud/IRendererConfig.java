package wtf.melonthedev.melonclient.gui.modhud;

import wtf.melonthedev.melonclient.mods.ModOptions;

public interface IRendererConfig {
    void save(ModOptions options);

    ModOptions load();
}
