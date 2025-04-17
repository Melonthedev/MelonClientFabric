package wtf.melonthedev.melonclient.melonclientwrapper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.GuiUtils;
import wtf.melonthedev.melonclient.utils.RenderUtil;

public class MelonScreen extends Screen {

    public final Minecraft mc = Client.getMinecraft();
    public Font font = mc.font;
    public Screen parent;
    public ClientLevel world;
    public AbstractClientPlayer player;
    public boolean drawMelonString = false;
    private boolean syncPlayerRotation = false;
    private boolean drawTitleString = false;
    private boolean drawTitleStringUnderLogo = false;
    private boolean drawDoneButton = false;

    //Static rotation fields
    private static float rotationSynced;
    private static float rotationSyncedLastTick;

    public MelonScreen(Component title) {
        super(title);
        Client.getInstance().addScreen(this);
    }
    public MelonScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
        Client.getInstance().addScreen(this);
    }
    public MelonScreen(Component title, boolean drawMelonString) {
        super(title);
        this.drawMelonString = drawMelonString;
        Client.getInstance().addScreen(this);
    }
    public MelonScreen(Component title, boolean drawMelonString, boolean drawTitleString) {
        super(title);
        this.drawMelonString = drawMelonString;
        this.drawTitleString = drawTitleString;
        Client.getInstance().addScreen(this);
    }
    public MelonScreen(Component title, Screen parent, boolean drawMelonString) {
        super(title);
        this.parent = parent;
        this.drawMelonString = drawMelonString;
        Client.getInstance().addScreen(this);
    }

    public void shouldSyncPlayerRotation(boolean syncPlayerRotation) {
        this.syncPlayerRotation = syncPlayerRotation;
    }
    public void shouldDrawTitleString(boolean drawTitleString) {
        this.drawTitleString = drawTitleString;
    }
    public void shouldDrawDoneButton(boolean drawDoneButton) {
        this.drawDoneButton = drawDoneButton;
    }
    public void shouldDrawTitleString(boolean drawTitleString, boolean drawTitleStringUnderLogo) {
        this.drawTitleString = drawTitleString;
        this.drawTitleStringUnderLogo = drawTitleStringUnderLogo;
    }

    @Override
    public void init() {
        Client.getInstance().removeScreen(this);
        Client.getInstance().addScreen(this);
        if (drawDoneButton) {
            this.addRenderableWidget(Button.builder(Component.literal("Back"), button -> {
                Client.setScreen(parent);
            }).bounds(this.width / 2 - 100, this.height / 4 + 132, 200, 20).build());

        }
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        if (drawMelonString)
            GuiUtils.drawMelonString(guiGraphics);
        if (drawTitleString) guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, drawTitleStringUnderLogo ? 90 : 15, 16777215);
    }



    @Override
    public void onClose() {
        Client.getInstance().removeScreen(this);
        super.onClose();
    }

    //Player Rendering
    float rotationLastTick;
    float rotation;

    public void onTick() {
        if (mc.screen != this) return;
        if (syncPlayerRotation) {
            rotationSyncedLastTick = rotationSynced;
            if(rotationSynced <= -360 || rotationSynced >= 360)
                rotationSynced = 0;
            rotationSynced += 4;
        } else {
            rotationLastTick = rotation;
            if(rotation <= -360 || rotation >= 360)
                rotation = 0;
            rotation += 4;
        }
    }
    public void loadPlayer() {
        //UUID uuid = Client.getMinecraft().getUser().getUuid() == null ? UUID.randomUUID() : UUID.fromString(Client.getMinecraft().getUser().getUuid());
        //world = new DummyWorld(new WorldInfo());
        //new DummyPlayer(this, world, new GameProfile(
        //uuid,
        //Client.getMinecraft().getUser().getName())
        //);
        player = mc.player != null ? mc.player : null;
        world = mc.level;
    }
    public void drawPlayerWithName(GuiGraphics guiGraphics, int x, int y, int scale, float partitialTicks) {
        drawPlayer(x, y, scale, partitialTicks);
        if (player == null) return;
        drawPlayerName(guiGraphics,  x - mc.font.width(player.getName()) / 2, y - 130);
    }
    boolean nullplayer;
    public void drawPlayer(int x, int y, int scale, float partitialTicks) {
        if (mc.player == null) {
            //mc.player = player;
            nullplayer = true;
        }
        float actualRotation;
        if (syncPlayerRotation) actualRotation = rotationSyncedLastTick + (rotationSynced - rotationSyncedLastTick) * partitialTicks;
        else actualRotation = rotationLastTick + (rotation - rotationLastTick) * partitialTicks;
        //Fixes rotate glich
        if (actualRotation < rotationSyncedLastTick && syncPlayerRotation) actualRotation = rotationSyncedLastTick;
        else if (actualRotation < rotationLastTick && !syncPlayerRotation) actualRotation = rotationLastTick;

        if (world != null && player != null)
            MelonClientWrapper.renderEntityInInventory(x, y, scale, actualRotation, 180, player, true);

        if (nullplayer) {
            //mc.player = null;
            nullplayer = false;
        }
    }
    public void drawPlayerName(GuiGraphics guiGraphics, int x, int y) {
        if (player == null) return;
        guiGraphics.drawString(mc.font, player.getName(), x, y, 0xFFFFFF);
    }


    //Draw Header
    public void drawMelonClientLogoHeader(GuiGraphics guiGraphics, int x, int y) {
        /*RenderSystem.setShaderTexture(0, ResourceLocation.parse("textures/gui/title/icon.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        blit(stack, x - 128, y, 0, 0, 128, 64, 128, 128);
        blit(stack, x, y, 0, 64, 128, 64, 128, 128);
        stack.popPose();*/
        RenderUtil.drawMelonClientLogo(guiGraphics, x, y);

        guiGraphics.drawString(font, "By Melonthedev                " + Client.getInstance().BASEVVERSION, width / 2 - 62, y + 55, 0xFFFFFF);
    }
}
