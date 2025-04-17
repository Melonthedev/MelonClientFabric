package wtf.melonthedev.melonclient;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import wtf.melonthedev.melonclient.capes.CapeManager;
import wtf.melonthedev.melonclient.discordrichpresence.DiscordRP;
import wtf.melonthedev.melonclient.discordrichpresence.DisplayedServer;
import wtf.melonthedev.melonclient.event.EventManager;
import wtf.melonthedev.melonclient.gui.modhud.HudManager;
import wtf.melonthedev.melonclient.gui.screens.WelcomeScreen;
import wtf.melonthedev.melonclient.listeners.TickListener;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;
import wtf.melonthedev.melonclient.mixin.MinecraftAccessor;
import wtf.melonthedev.melonclient.mods.ModInstanceManager;
import wtf.melonthedev.melonclient.utils.ClientUtils;
import wtf.melonthedev.melonclient.utils.DefaultValues;
import wtf.melonthedev.melonclient.utils.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    private static final Client INSTANCE = new Client();
    public String NAME = "MelonClient", VERSION = "0.2 Beta", VVERSION = "v0.2 Beta", NAMEVERSION = NAME + " " + VERSION, NAMEVVERSION = NAME + " " + VVERSION, BASEVVERSION = "v.0.2";
    private Logger LOGGER = LogUtils.getLogger();
    private final DiscordRP discordRP = new DiscordRP();
    private HudManager hudManager;
    private Minecraft mc;
    public static boolean isNewUser = false;
    public static boolean showWelcomeScreen = false;
    public static int inventoryTextColor = 4210752;
    private final List<MelonScreen> melonScreenList = new ArrayList<>();
    public Component[] credits = DefaultValues.credits;

    //URLs
    public static String apiUrl = "https://melonclient.melonthedev.wtf/api/";
    //public static String apiUrl = "http://localhost:5001/api";
    public static String discordUrl = "https://discord.gg/8qZ3Z7Y";
    public static String websiteUrl = "https://melonclient.melonthedev.wtf/";
    public static String gettingStartedUrl = "https://melonclient.melonthedev.wtf/getting-started";
    public static String friendsUrl = "https://melonclient.melonthedev.wtf/profile/friends";

    //Exclusive to 1.19
    public static EntityRendererProvider.Context entityRendererContext;
    public static boolean upsideDown = false;
    public static boolean noRotateBoundsInInventory = true;

    public void postInitialize() {}

    public void initialize() {
        this.mc = Client.getMinecraft();
        this.LOGGER = LogUtils.getLogger();
        if (!ClientUtils.hasUsedClientBefore()) {
            isNewUser = true;
            showWelcomeScreen = true;
            ApiWrapper.handleNewMelonClientUser();
        }
        discordRP.init();
        FileManager.init();
        ApiWrapper.init();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runEndless();
            }
        }, 0, 2000);
        EventManager.register(new TickListener());
        start();
    }

    public void start() {
        hudManager = HudManager.getINSTANCE();
        ModInstanceManager.register(hudManager);
        CapeManager.checkForApiChanges();
    }

    public static void openStartScreen() {
        if (Client.showWelcomeScreen)
            setScreen(new WelcomeScreen());
        else setScreen(new TitleScreen(true));
    }

    public void startIngame() {
        if (isDevModeEnabled()) Client.getInstance().getLOGGER().debug("IngameMode Started");
        if (mc.hasSingleplayerServer())
            getDiscordRP().updateSingleplayer(true);
        else if (mc.getCurrentServer() != null)
            DisplayedServer.displayServer(mc.getCurrentServer().ip);
    }

    public void runEndless() {
        discordRP.handleMiniGame();
    }

    public void shutdown() {
        discordRP.shutdown();
    }

    public Logger getLOGGER() {
        return LOGGER;
    }

    public DiscordRP getDiscordRP() {
        return discordRP;
    }

    public static Client getInstance() {
        return INSTANCE;
    }
    
    public static Minecraft getMinecraft() {
        return MelonClientWrapper.getMinecraft();
    }

    public HudManager getHudManager() {
        return hudManager;
    }

    public void addScreen(MelonScreen screen) {
        melonScreenList.add(screen);
    }
    public void removeScreen(MelonScreen screen) {
        melonScreenList.remove(screen);
    }

    public static void onResourceReload() {
        getInstance().getLOGGER().info("ResourceReload");
        JackPackIntegration.onReload();
    }

    public static void onKey() {
        if (ModInstanceManager.modPerspective != null && getMinecraft().player != null)
            ModInstanceManager.modPerspective.handleKeyboard();
    }

    public static void onTick() {
        for (MelonScreen screen : getInstance().melonScreenList) {
            screen.onTick();
        }
    }

    public static int getInventoryTextColor() {
        return inventoryTextColor;
    }

    public static void setScreen(Screen screen) {
        MelonClientWrapper.setScreen(screen);
    }

    public static boolean isDevModeEnabled() {
        return true; //Client.getMinecraft().options.melonEnableDeveloperMode;
    }

}
