package wtf.melonthedev.melonclient.utils;

import com.google.common.hash.Hashing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.FilenameUtils;
import wtf.melonthedev.melonclient.Client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;

public class UrlTextureUtil
{
    public static ResourceLocation registerTexture(String url, ResourceLocationCallback callback) {
        String baseName = FilenameUtils.getBaseName(url).toLowerCase();
        final ResourceLocation resourcelocation = ResourceLocation.parse("melonclient/assets/" + baseName);
        AbstractTexture abstracttexture = Client.getMinecraft().getTextureManager().getTexture(resourcelocation, MissingTextureAtlasSprite.getTexture());
        if (abstracttexture == MissingTextureAtlasSprite.getTexture()) {
            CompletableFuture.runAsync(() -> download(baseName, callback, url, resourcelocation));
        } else if (callback != null) {
            hasFileChanged(baseName, url, hasChanged -> {
                if (hasChanged) {
                    CompletableFuture.runAsync(() -> download(baseName, callback, url, resourcelocation));
                } else {
                    callback.onTextureLoaded(resourcelocation);
                }
            });
        }
        return resourcelocation;
    }

    private static void download(String baseName, ResourceLocationCallback callback, String url, ResourceLocation resourcelocation)
    {
        File file = new File(Client.getMinecraft().gameDirectory.getAbsolutePath() + "/melonclient/assets/" + baseName);
        HttpTexture httptexture = new HttpTexture(file, url, DefaultPlayerSkin.getDefaultTexture(), false, () -> {
            if (callback != null) callback.onTextureLoaded(resourcelocation);
            System.out.println("Downloaded texture " + url + " to " + file.getAbsolutePath());
        });
        CompletableFuture.runAsync(() -> Client.getMinecraft().getTextureManager().register(resourcelocation, httptexture));
    }

    private static boolean hasFileChanged(String baseName, String url, ChangedFileCallback callback)
    {
        File file = new File(Client.getMinecraft().gameDirectory, "melonclient/assets/" + baseName);
        File fileTemp = new File(Client.getMinecraft().gameDirectory, "melonclient/assets/" + baseName + "-temp");
        if (!file.exists())
            return true;
        CompletableFuture.runAsync(() -> {
            try {
                InputStream in = new URL(url).openStream();
                Files.copy(in, Path.of(Client.getMinecraft().gameDirectory.getAbsolutePath(), "melonclient/assets/" + baseName + "-temp"), StandardCopyOption.REPLACE_EXISTING);
                in.close();
                String hashOriginal = com.google.common.io.Files.asByteSource(file).hash(Hashing.crc32()).toString();
                String hashNew = com.google.common.io.Files.asByteSource(fileTemp).hash(Hashing.crc32()).toString();
                System.out.println("Cape " + baseName + " hash original: " + hashOriginal + " new: " + hashNew + " -- " + (hashOriginal.equals(hashNew) ? "SIMILAR" : "DIFFERENT!"));
                fileTemp.delete();
                callback.onHashCompareResult(!hashOriginal.equals(hashNew));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return false;
    }

    public interface ResourceLocationCallback {
        void onTextureLoaded(ResourceLocation rl);
    }
    public interface ChangedFileCallback {
        void onHashCompareResult(boolean hasChanged);
    }
}