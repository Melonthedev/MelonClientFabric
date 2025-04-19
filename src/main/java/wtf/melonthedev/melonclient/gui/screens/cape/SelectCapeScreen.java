package wtf.melonthedev.melonclient.gui.screens.cape;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.networking.ApiWrapper;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.cape.CapeManager;
import wtf.melonthedev.melonclient.cape.MelonCape;
import wtf.melonthedev.melonclient.cape.MelonCapeAnimated;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

import java.util.HashMap;
import java.util.UUID;

import static wtf.melonthedev.melonclient.utils.GuiUtils.drawHollowRect;

public class SelectCapeScreen extends MelonScreen {

    private HashMap<Integer, MelonCape> capes = new HashMap<>();
    private int viewedCape = 0;

    protected SelectCapeScreen() {
        super(Component.literal("Select Cape"), true);
        minecraft = Client.getMinecraft();
        if (minecraft.player == null) return;
        capes = new HashMap<>();
        UUID uuid = minecraft.player.getGameProfile().getId();
        ApiWrapper.getSelectedCapeIdAsync(uuid, response -> viewedCape = response);
        /*for (int i : ApiWrapper.getCapeIds(uuid)) {
            if (ApiWrapper.isAnimated(uuid, i)) {
                MelonCapeAnimated capeAnimated = new MelonCapeAnimated();
                //ApiWrapper.setSelectedCape(uuid, i);
                //capeAnimated.initCape(uuid);
                capeAnimated.setOwner(uuid);
                capeAnimated.loadCapeLocal(i);
                capes.put(i, capeAnimated);
                } else {
                MelonCape cape = new MelonCape();
                //ApiWrapper.setSelectedCape(uuid, i);
                //cape.initCape(uuid);
                cape.setOwner(uuid);
                cape.loadCapeLocal(i);
                capes.put(i, cape);
                //}
                }
        }*/
        ApiWrapper.getCapeIdsAsync(uuid, response -> {
            for (int i : response) {
                ApiWrapper.isAnimatedAsync(uuid, i, isAnimated -> {
                    if (isAnimated) {
                        MelonCapeAnimated capeAnimated = new MelonCapeAnimated();
                        //ApiWrapper.setSelectedCape(uuid, i);
                        //capeAnimated.initCape(uuid);
                        capeAnimated.setOwner(uuid);
                        capeAnimated.loadCapeLocal(i);
                        capes.put(i, capeAnimated);
                    } else {
                        MelonCape cape = new MelonCape();
                        //ApiWrapper.setSelectedCape(uuid, i);
                        //cape.initCape(uuid);
                        cape.setOwner(uuid);
                        cape.loadCapeLocal(i);
                        capes.put(i, cape);
                        //}
                    }
                });
            }
        });
    }

    @Override
    public void init() {
        super.init();
        minecraft = Client.getMinecraft();
        if (minecraft.player == null) return;
        addRenderableWidget(Button.builder(Component.literal(">"), (p_96321_) -> nextCape()).bounds(width - 60, height/3 - 60, 20, 20).build());
        addRenderableWidget(Button.builder(Component.literal("<"), (p_96321_) -> previousCape()).bounds(40, height/3 - 60, 20, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Select"), (p_96321_) -> {
            CapeManager.selectCape(minecraft.player, viewedCape);
            Client.setScreen(null);
        }).bounds(width/2 - 40, height/3 + 110, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Disable"), (p_96321_) -> CapeManager.disableCape(minecraft.player.getGameProfile().getId())).bounds(width/2 - 40, height/3 + 135, 80, 20).build());
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        super.renderBackground(guiGraphics, mouseX, mouseY, ticks);
        super.render(guiGraphics, mouseX, mouseY, ticks);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        renderCapeImages(guiGraphics);
    }

    private void nextCape() {
        if (viewedCape < capes.size() - 1)
            viewedCape++;
        ApiWrapper.getSelectedCapeIdAsync(minecraft.player.getGameProfile().getId(), response -> {
            if (viewedCape != response)
            CapeManager.selectLocalCape(minecraft.player, viewedCape);
        });
    }

    private void previousCape() {
        if (viewedCape > 0)
            viewedCape--;
        ApiWrapper.getSelectedCapeIdAsync(minecraft.player.getGameProfile().getId(), response -> {
            if (viewedCape != response)
                CapeManager.selectLocalCape(minecraft.player, viewedCape);
        });
    }

    @Override
    public void onClose() {
        super.onClose();
        if (minecraft.player != null) CapeManager.loadCape(minecraft.player.getGameProfile().getId());
    }


    float playerPitch = 1.0F;
    private void renderCapeImages(GuiGraphics guiGraphics) {
        playerPitch++;
        //if (minecraft.player != null) MelonClientWrapper.renderEntityInInventory(width/2, height/3 + 100, 60, playerPitch, 1.0f, this.minecraft.player, true);


        int c = -3;
        for (int i = 1; i <= 5; i++) {
            c++;
            if (c == 0) continue;
            if (capes.get(viewedCape + c) == null)
                continue;
            MelonCape cape = capes.get(viewedCape + c);
            ResourceLocation rl = cape.getTextureLocation(); //(cape instanceof MelonCapeAnimated) ? ((MelonCapeAnimated) cape).getCurrentFrameTexture() :
            if (rl == null) continue;
            //System.out.println(new ScaledResolution(mc).getScaledWidth());
            int imgSizeX = 170;
            guiGraphics.pose().pushPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (width < 870) {
                imgSizeX = 80;
                drawHollowRect(guiGraphics, (width/5)*i - imgSizeX - 10, height/2 - 30 - 10, imgSizeX, 80, 0xFF808080);
                //mc.getTextureManager().bindTexture(rl);
                guiGraphics.blit(rl, (width/5)*i - imgSizeX - 10 + 5, height/2 - 30, 0, 0, imgSizeX, 80, 200, 100);
            } else {
                guiGraphics.drawString(mc.font, "Elytra", (width/5)*i - 70, height/2 - 30 - 20, 0xFF808080);
                guiGraphics.drawString(mc.font, "None", (width/5)*i - 55, height/2 - 30 + 20, 0xFF808080);
                drawHollowRect(guiGraphics, (width/5)*i - imgSizeX - 10, height/2 - 30 - 10, imgSizeX, 90, 0xFF808080);
                guiGraphics.blit(rl, (width/5)*i - imgSizeX, height/2 - 30, 0, 0, imgSizeX, 80, 200, 100);
            }
            guiGraphics.drawString(mc.font, "Cape", (width/5)*i - imgSizeX - 10, height/2 - 30 - 20, 0xFF808080);
            guiGraphics.pose().popPose();
            //drawTexturedModalRect((width/5)*i - 150, height/2 - 50, 0, 0, imgSizeX, 80);
            //int imgSizeX = 200;
            //if (minecraft.getWindow().getGuiScaledWidth() < 730) imgSizeX = 95;
            //drawHollowRect(stack, (width/5)*i - 160, height/2 - 50 - 10, imgSizeX, 80, 0xFF808080);
            //GuiUtils.drawTexture(stack, (width/5)*i - 150, height/2 - 50, -100, 0, 0, imgSizeX, 100, 200, 100, rl, this);
        }
    }


}
