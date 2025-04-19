package wtf.melonthedev.melonclient.cape;

import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.networking.ApiWrapper;
import wtf.melonthedev.melonclient.utils.UrlTextureUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MelonCapeAnimated extends MelonCape {

    private int frameCount;
    private int currentFrame;
    private int frameDelay;
    private final HashMap<Integer, ResourceLocation> frames = new HashMap<>();
    private Timer timer;

    public MelonCapeAnimated(ResourceLocation textureLocation, int capeId, int frameCount, int frameDelay) {
        super(textureLocation, capeId);
        this.frameCount = frameCount;
        this.frameDelay = frameDelay;
    }

    public MelonCapeAnimated() {}

    @Override
    public void initCape(UUID owner) {
        super.initCape(owner);
        ApiWrapper.getCapeAsync(owner, getCapeId(), cape -> ApiWrapper.isAnimatedAsync(owner, getCapeId(), animated -> {
            if (!animated
                    || cape == null
                    || !cape.has("animationFrames")
                    || !cape.has("frameDelay")) {
                throw new IllegalStateException("Cape is not animated");
            }
            this.currentFrame = 0;
            this.frameCount = cape.get("animationFrames").getAsInt();
            this.frameDelay = cape.get("frameDelay").getAsInt();
        }));
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < frameCount; i++) {
                int y = i;
                UrlTextureUtil.registerTexture(CapeManager.CAPE_URL + getOwner() + "/animated" + getCapeId() + "/frame" + i + ".png",
                        rl -> frames.put(y, rl)
                );
            }
            UrlTextureUtil.registerTexture(CapeManager.CAPE_URL + owner + "/animated" + getCapeId() + "/frame0.png", this::setTextureLocation);
        });
    }

    @Override
    public void loadCape() {
        super.loadCape();
        if (getCapeId() == 0) return;
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < frameCount; i++) {
                int y = i;
                UrlTextureUtil.registerTexture(CapeManager.CAPE_URL + getOwner() + "/animated" + getCapeId() + "/frame" + i + ".png",
                        rl -> frames.put(y, rl)
                );
            }
        });
        runCapeUpdates();
    }

    private void runCapeUpdates() {
        if (timer != null) timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateCape();

            }
        }, 0, frameDelay);
    }

    private void updateCape() {
        if (currentFrame >= frameCount) {
            currentFrame = 0;
        }
        currentFrame++;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public ResourceLocation getCurrentFrameTexture() {
        return frames.get(currentFrame);
    }

    public int getFrameDelay() {
        return frameDelay;
    }

    public HashMap<Integer, ResourceLocation> getFrames() {
        return frames;
    }
}
