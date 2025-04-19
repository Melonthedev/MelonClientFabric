package wtf.melonthedev.melonclient.modengine;

import net.minecraft.ChatFormatting;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;
import wtf.melonthedev.melonclient.utils.BackgroundColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModOptions {

    public ScreenPosition position;
    public ChatFormatting color;
    public BackgroundColor backgroundColor;
    public boolean backgroundEnabled;
    public List<ModFlag> flags;
    public boolean enabled;
    public float scale;
    public boolean showModName;
    public int modType;
    public int modTypeCount;
    public String description;


    public void init() {
        //SET DEFAULTS
        Random random = new Random();
        position = new ScreenPosition(random.nextInt(350), random.nextInt(240));
        color = ChatFormatting.WHITE;
        flags = new ArrayList<>();
        enabled = true;
        backgroundColor = null;
        backgroundEnabled = false;
        scale = 0.1f;
        showModName = true;
        description = "DummyDescription";
    }

    public void checkForUninitializedOptions() {
        if (position == null) position = new ScreenPosition(new Random().nextInt(350), new Random().nextInt(240));
        if (color == null) color = ChatFormatting.WHITE;
        if (flags == null) flags = new ArrayList<>();
        if (scale == 0) scale = 0.1f;
        if (description == null) description = "DummyDescription";
    }

}
