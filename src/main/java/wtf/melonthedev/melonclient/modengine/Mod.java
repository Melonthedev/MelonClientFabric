package wtf.melonthedev.melonclient.modengine;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.utils.FileManager;

import java.io.File;

public class Mod {

    private String name;
    private String id;
    protected ModOptions options;
    private ResourceLocation icon;

    protected String OPTIONS_FILE_NAME = "options.json";

    protected final Minecraft mc;
    protected final Client client;

    public Mod(String name, String id) {
        this.mc = Client.getMinecraft();
        this.client = Client.getInstance();
        this.id = id;
        this.name = name == null ? "MelonClientMod" : name;
        this.icon = ResourceLocation.fromNamespaceAndPath("melonclient", "modicons/" + getIdentifier() + ".png");
        this.loadOptionsFromFile();
    }

    public void setEnabled(boolean isEnabled) {
        getOptions().enabled = isEnabled;
        save();
    }

    public boolean isEnabled() {
        return getOptions().enabled;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return id;
    }

    public ModOptions getOptions() {
        return options;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public void save() {
        saveOptionsToFile();
    }

    public File getModFolder() {
        File folder = new File(FileManager.getModsDir(), this.getIdentifier());
        folder.mkdirs();
        return folder;
    }

    protected void loadOptionsFromFile() {
        ModOptions loadedOptions = FileManager.readFromJson(new File(getModFolder(), OPTIONS_FILE_NAME), ModOptions.class);
        if (loadedOptions == null)
            loadedOptions = new ModOptions();
        this.options = loadedOptions;
    }

    public void saveOptionsToFile() {
        FileManager.writeJsonToFile(new File(getModFolder(), OPTIONS_FILE_NAME), options);
    }


}
