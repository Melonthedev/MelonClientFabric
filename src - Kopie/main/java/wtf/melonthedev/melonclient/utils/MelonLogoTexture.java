package wtf.melonthedev.melonclient.utils;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;

public class MelonLogoTexture extends SimpleTexture {

    public static final ResourceLocation MELONCLIENT_LOGO_LOCATION = ResourceLocation.parse("textures/gui/title/melonclient.png");

    public static SimpleTexture.TextureImage simpletexture;

    public MelonLogoTexture() {
        super(MELONCLIENT_LOGO_LOCATION);
    }
    public SimpleTexture.TextureImage getTextureImage(ResourceManager p_96194_) {
        Minecraft minecraft = Minecraft.getInstance();
        VanillaPackResources vanillapackresources = minecraft.getVanillaPackResources();

        try {
            InputStream inputstream = vanillapackresources.getResource(PackType.CLIENT_RESOURCES, MELONCLIENT_LOGO_LOCATION).get();

            //SimpleTexture.TextureImage simpletexture$textureimage;
            try {
                simpletexture = new SimpleTexture.TextureImage(new TextureMetadataSection(true, true), NativeImage.read(inputstream));
            } catch (Throwable throwable1) {
                if (inputstream != null) {
                    try {
                        inputstream.close();
                    } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                    }
                }

                throw throwable1;
            }

            if (inputstream != null) {
                inputstream.close();
            }

            return simpletexture;
        } catch (IOException ioexception) {
            return new SimpleTexture.TextureImage(ioexception);
        }
    }
}