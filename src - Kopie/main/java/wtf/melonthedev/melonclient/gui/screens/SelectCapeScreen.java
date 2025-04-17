package wtf.melonthedev.melonclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.ApiWrapper;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.capes.CapeManager;
import wtf.melonthedev.melonclient.capes.MelonCape;
import wtf.melonthedev.melonclient.capes.MelonCapeAnimated;
import wtf.melonthedev.melonclient.gui.GuiUtils;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonClientWrapper;
import wtf.melonthedev.melonclient.melonclientwrapper.MelonScreen;

import java.util.HashMap;
import java.util.UUID;

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
        addRenderableWidget(new Button(width - 60, height/3 - 60, 20, 20, Component.literal(">"), (p_96321_) -> nextCape()));
        addRenderableWidget(new Button(40, height/3 - 60, 20, 20, Component.literal("<"), (p_96321_) -> previousCape()));
        addRenderableWidget(new Button(width/2 - 40, height/3 + 110, 80, 20, Component.literal("Select"), (p_96321_) -> {
            CapeManager.selectCape(minecraft.player, viewedCape);
            Client.setScreen(null);
        }));
        addRenderableWidget(new Button(width/2 - 40, height/3 + 135, 80, 20, Component.literal("Disable"), (p_96321_) -> CapeManager.disableCape(minecraft.player.getGameProfile().getId())));
    }


    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float ticks) {
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, ticks);
        drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 16777215);
        renderCapeImages(stack);
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

    private void drawHollowRect(PoseStack stack, int x, int y, int width, int height, int color) {
        this.hLine(stack, x, x + width, y, color);
        this.hLine(stack, x, x + width, y + height, color);
        this.vLine(stack, x, y + height, y, color);
        this.vLine(stack, x + width, y, y + height, color);
    }

    float playerPitch = 1.0F;
    private void renderCapeImages(PoseStack stack) {
        playerPitch++;
        if (minecraft.player != null) MelonClientWrapper.renderEntityInInventory(width/2, height/3 + 100, 60, playerPitch, 1.0f, this.minecraft.player, true);


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
            stack.pushPose();
            RenderSystem.setShaderTexture(0, rl);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (width < 870) {
                imgSizeX = 80;
                drawHollowRect(stack, (width/5)*i - imgSizeX - 10, height/2 - 30 - 10, imgSizeX, 80, 0xFF808080);
                //mc.getTextureManager().bindTexture(rl);
                blit(stack, (width/5)*i - imgSizeX - 10 + 5, height/2 - 30, 0, 0, imgSizeX, 80, 200, 100);
            } else {
                drawString(stack, mc.font, "Elytra", (width/5)*i - 70, height/2 - 30 - 20, 0xFF808080);
                drawString(stack, mc.font, "None", (width/5)*i - 55, height/2 - 30 + 20, 0xFF808080);
                drawHollowRect(stack, (width/5)*i - imgSizeX - 10, height/2 - 30 - 10, imgSizeX, 90, 0xFF808080);
                blit(stack, (width/5)*i - imgSizeX, height/2 - 30, 0, 0, imgSizeX, 80, 200, 100);
            }
            drawString(stack, mc.font, "Cape", (width/5)*i - imgSizeX - 10, height/2 - 30 - 20, 0xFF808080);
            stack.popPose();
            //drawTexturedModalRect((width/5)*i - 150, height/2 - 50, 0, 0, imgSizeX, 80);
            //int imgSizeX = 200;
            //if (minecraft.getWindow().getGuiScaledWidth() < 730) imgSizeX = 95;
            //drawHollowRect(stack, (width/5)*i - 160, height/2 - 50 - 10, imgSizeX, 80, 0xFF808080);
            //GuiUtils.drawTexture(stack, (width/5)*i - 150, height/2 - 50, -100, 0, 0, imgSizeX, 100, 200, 100, rl, this);
        }
    }


}
