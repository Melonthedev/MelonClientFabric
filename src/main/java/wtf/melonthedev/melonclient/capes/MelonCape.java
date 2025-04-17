package wtf.melonthedev.melonclient.capes;

import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.ApiWrapper;
import wtf.melonthedev.melonclient.utils.UrlTextureUtil;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MelonCape {

    private ResourceLocation textureLocation;
    private int capeId;
    private UUID owner;

    public MelonCape(ResourceLocation textureLocation, int capeId) {
        this.textureLocation = textureLocation;
        this.capeId = capeId;
    }

    public MelonCape() {}

    public void initCape(UUID owner) {
        this.owner = owner;
    }

    public void loadCape() {
        CompletableFuture.runAsync(() -> {
            ApiWrapper.getSelectedCapeIdAsync(owner, response -> this.capeId = response);
            if (capeId == 0) {
                textureLocation = null;
                return;
            }
            UrlTextureUtil.registerTexture(CapeManager.CAPE_URL + owner + "/cape" + capeId + ".png", rl -> textureLocation = rl);
        });
    }

    public void loadCapeLocal(int capeId) {
        CompletableFuture.runAsync(() -> {
            this.capeId = capeId;
            if (capeId == 0) {
                textureLocation = null;
                return;
            }
            UrlTextureUtil.registerTexture(CapeManager.CAPE_URL + owner + "/cape" + capeId + ".png", rl -> textureLocation = rl);
        });
    }

    public boolean isAnimated() {
        return false;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public void setTextureLocation(ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }

    public int getCapeId() {
        return capeId;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
