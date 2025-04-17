package wtf.melonthedev.melonclient.utils;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MelonLogoTexture extends SimpleTexture {

    public static final ResourceLocation MELONCLIENT_LOGO_LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/title/melonclient.png");

    public static SimpleTexture.TextureImage simpletexture;

    public MelonLogoTexture() {
        super(MELONCLIENT_LOGO_LOCATION);
    }

    public SimpleTexture.TextureImage getTextureImage(ResourceManager p_96194_) {
        Minecraft minecraft = Minecraft.getInstance();
        VanillaPackResources vanillaPackResources = minecraft.getVanillaPackResources();
        try {
            IoSupplier<InputStream> ioSupplier = vanillaPackResources.getResource(PackType.CLIENT_RESOURCES, MELONCLIENT_LOGO_LOCATION);
            if (ioSupplier == null) {
                return new SimpleTexture.TextureImage(new FileNotFoundException(MELONCLIENT_LOGO_LOCATION.toString()));
            }
            //SimpleTexture.TextureImage simpletexture$textureimage;
            InputStream inputStream = ioSupplier.get();
            try {
                simpletexture = new SimpleTexture.TextureImage(new TextureMetadataSection(true, true), NativeImage.read(inputStream));
            } catch (Throwable throwable1) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                    }
                }

                throw throwable1;
            }

            if (inputStream != null) {
                inputStream.close();
            }

            return simpletexture;
        } catch (IOException ioexception) {
            return new SimpleTexture.TextureImage(ioexception);
        }
    }
}