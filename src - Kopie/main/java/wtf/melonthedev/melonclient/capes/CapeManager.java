package wtf.melonthedev.melonclient.cape;

import net.minecraft.client.player.AbstractClientPlayer;
import wtf.melonthedev.melonclient.ApiWrapper;
import wtf.melonthedev.melonclient.Client;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class CapeManager {

    public static final String CAPE_URL = "https://melonclient.melonthedev.wtf/cape/";
    public static final ConcurrentHashMap<UUID, MelonCape> capes = new ConcurrentHashMap<>();

    private CapeManager() {} //NOT TO BE INSTANTIATED

    public static void loadCape(UUID uuid) {
        CompletableFuture.runAsync(() -> {
            if (!capes.containsKey(uuid)) {
                getCapeInstanceAsync(uuid, capeInstance -> {
                    capes.put(uuid, capeInstance);
                    capes.get(uuid).initCape(uuid);
                    capes.get(uuid).loadCape();
                });
            }
            else capes.get(uuid).loadCape();
        });
    }

    public static void reloadCape(UUID uuid) {
        capes.remove(uuid);
        loadCape(uuid);
    }
    
    public static void disableCape(UUID uuid) {
        ApiWrapper.setSelectedCapeAsync(uuid, 0);
        reloadCape(uuid);
    }

    public static MelonCape getSelectedCape(UUID uuid) {
        return capes.get(uuid);
    }

    public static void selectCape(AbstractClientPlayer player, int i) {
        ApiWrapper.hasCapeWithIdAsync(player.getGameProfile().getId(), i, hasCape -> {
            if (!hasCape) {
                Client.getInstance().getLOGGER().warn("NO CAPE WITH ID " + i + " FOUND FOR PLAYER " + player);
                return;
            }
            ApiWrapper.setSelectedCapeAsync(player.getGameProfile().getId(), i);
            reloadCape(player.getGameProfile().getId());
        });
    }

    public static void selectLocalCape(AbstractClientPlayer player, int i) { //PLEASE CHECK AGAIN MELON
        capes.remove(player.getGameProfile().getId());
        UUID uuid = player.getGameProfile().getId();
        if (!capes.containsKey(uuid)) {
            getCapeInstanceAsync(uuid, cape -> {
                capes.put(uuid, cape);
                capes.get(uuid).initCape(uuid);
                capes.get(uuid).loadCapeLocal(i);
            });
            return;
        }
        capes.get(uuid).loadCapeLocal(i);
    }

    public static void getCapeInstanceAsync(UUID uuid, MelonCapeCallback callback) {
        ApiWrapper.getSelectedCapeIdAsync(uuid, selectedCapeId -> ApiWrapper.isAnimatedAsync(uuid, selectedCapeId, animated -> {
            if (animated) callback.onCallback(new MelonCapeAnimated());
            else callback.onCallback(new MelonCape());
        }));
    }

    public static void checkForApiChanges() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                CompletableFuture.runAsync(() -> {
                    for (UUID uuid : capes.keySet()) { //on new player join abstractplayerclass will automatically call loadcape which will add it to hashmap
                        if (capes.get(uuid) == null) continue;
                        if (Client.getMinecraft().getCurrentServer() == null && !Client.getMinecraft().hasSingleplayerServer())
                            capes.clear();
                        ApiWrapper.getSelectedCapeIdAsync(uuid, response -> {
                            if (response != capes.get(uuid).getCapeId()) reloadCape(uuid);
                        });
                    }
                });
            }
        }, 0, 1000);
    }

    public interface MelonCapeCallback {
        void onCallback(MelonCape cape);
    }
}
