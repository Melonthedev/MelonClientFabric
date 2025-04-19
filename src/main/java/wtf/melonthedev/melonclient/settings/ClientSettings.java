package wtf.melonthedev.melonclient.settings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.OptionInstance;

import java.io.File;

public class ClientSettings {

    public static File melonOptionsFile;

    //MelonClient Options
    public static OptionInstance<Boolean> useOldTextures = OptionInstance.createBoolean("options.melon.useoldtextures", false);
    public static boolean showMelonTabIcon = true;
    public static boolean useScrollWheelZoom = true;
    public static boolean disableRotateBoundsInInventory = false;
    public static boolean showCrosshairInThirdPerson = true;
    public static boolean disablePumpkinBlur = false;
    public static boolean disableSpyglassOverlay = false;
    public static boolean disablePlayerPreviewInPauseMenu = false;
    public static boolean disableMelonClientLabel = false;
    public static boolean renderOwnNameTag = false;
    public static boolean enableDeveloperMode = false;
    public static boolean confirmExit = true;
    public static boolean showSplashTexts = false;
    public static String customApiUrl = "";

    //Keybinds
    public static final KeyMapping keyMelonHudEditor = new KeyMapping("HUD Editor", InputConstants.KEY_RSHIFT, "MelonClient");
    public static final KeyMapping keyMelonPerspective = new KeyMapping("Free Perspective", InputConstants.KEY_R, "MelonClient");
    public static final KeyMapping keyMelonZoom = new KeyMapping("Zoom", InputConstants.KEY_C, "MelonClient");

    public static void init(File file) {
        melonOptionsFile = new File(file, "optionsmelonclient.txt");
    }

    /*public static void processSettings(Options.FieldAccess options) {
        options.process("melonUseOldTextures", useOldTextures);
        options.process("melonShowMelonTabIcon", showMelonTabIcon);
        options.process("melonUseScrollWheelZoom", useScrollWheelZoom);
        options.process("melonDisableRotateBoundsInInventory", disableRotateBoundsInInventory);
        options.process("melonShowCrosshairInThirdPerson", showCrosshairInThirdPerson);
        options.process("melonDisablePumpkinBlur", disablePumpkinBlur);
        options.process("melonDisableSpyglassOverlay", disableSpyglassOverlay);
        options.process("melonDisablePlayerPreviewInPauseMenu", disablePlayerPreviewInPauseMenu);
        options.process("melonDisableMelonClientLabel", disableMelonClientLabel);
        options.process("melonRenderOwnNameTag", renderOwnNameTag);
        options.process("enableDeveloperMode", enableDeveloperMode);
        options.process("melonConfirmExit", confirmExit);
        options.process("melonShowSplashTexts", showSplashTexts);
        options.process("customApiUrl", customApiUrl);
    }

    public static void loadMelonSettings() {
        try {
            if (!ClientSettings.melonOptionsFile.exists())
                return;
            CompoundTag compoundtag = new CompoundTag();
            BufferedReader bufferedreader = Files.newReader(ClientSettings.melonOptionsFile, Charsets.UTF_8);

            try {
                bufferedreader.lines().forEach((line) -> {
                    try {
                        Iterator<String> iterator = Options.OPTION_SPLITTER.split(line).iterator();
                        compoundtag.putString(iterator.next(), iterator.next());
                    } catch (Exception exception1) {
                        Options.LOGGER.warn("Skipping bad option: {}", (Object) line);
                    }
                });
            } catch (Throwable throwable1) {
                if (bufferedreader != null) {
                    try {
                        bufferedreader.close();
                    } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                    }
                }
                throw throwable1;
            }
            if (bufferedreader != null)
                bufferedreader.close();
            final CompoundTag compoundtag1 = Client.getMinecraft().options.dataFix(compoundtag);

            ClientSettings.processSettings(new Options.FieldAccess() {
                @Nullable
                private String getValueOrNull(String key) {
                    return compoundtag1.contains(key) ? compoundtag1.getString(key) : null;
                }

                public <T> void process(String p_232125_, OptionInstance<T> p_232126_) {
                    String s = this.getValueOrNull(p_232125_);
                    if (s != null) {
                        JsonReader jsonreader = new JsonReader(new StringReader(s.isEmpty() ? "\"\"" : s));
                        JsonElement jsonelement = JsonParser.parseReader(jsonreader);
                        DataResult<T> dataresult = p_232126_.codec().parse(JsonOps.INSTANCE, jsonelement);
                        dataresult.error().ifPresent((p_232130_) -> {
                            Options.LOGGER.error("Error parsing option value " + s + " for option " + p_232126_ + ": " + p_232130_.message());
                        });
                        dataresult.result().ifPresent(p_232126_::set);
                    }

                }

                public int process(String p_168467_, int p_168468_) {
                    String s = this.getValueOrNull(p_168467_);
                    if (s != null) {
                        try {
                            return Integer.parseInt(s);
                        } catch (NumberFormatException numberformatexception) {
                            Options.LOGGER.warn("Invalid integer value for option {} = {}", p_168467_, s, numberformatexception);
                        }
                    }

                    return p_168468_;
                }

                public boolean process(String p_168483_, boolean p_168484_) {
                    String s = this.getValueOrNull(p_168483_);
                    return s != null ? Options.isTrue(s) : p_168484_;
                }

                public String process(String p_168480_, String p_168481_) {
                    return MoreObjects.firstNonNull(this.getValueOrNull(p_168480_), p_168481_);
                }

                public float process(String p_168464_, float p_168465_) {
                    String s = this.getValueOrNull(p_168464_);
                    if (s != null) {
                        if (Options.isTrue(s)) {
                            return 1.0F;
                        }

                        if (Options.isFalse(s)) {
                            return 0.0F;
                        }

                        try {
                            return Float.parseFloat(s);
                        } catch (NumberFormatException numberformatexception) {
                            Options.LOGGER.warn("Invalid floating point value for option {} = {}", p_168464_, s, numberformatexception);
                        }
                    }

                    return p_168465_;
                }

                public <T> T process(String p_168470_, T p_168471_, Function<String, T> p_168472_, Function<T, String> p_168473_) {
                    String s = this.getValueOrNull(p_168470_);
                    return (T)(s == null ? p_168471_ : p_168472_.apply(s));
                }
            });
        } catch (Exception exception) {
            Options.LOGGER.error("Failed to load melonclient options", (Throwable) exception);
        }
    }*/

    public static KeyMapping[] getMelonClientKeyMappings() {
        return new KeyMapping[] {keyMelonHudEditor, keyMelonPerspective, keyMelonZoom};
    }
    
}
