package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerLinksScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerLinks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.gui.screens.MelonClientMultiplayerScreen;
import wtf.melonthedev.melonclient.gui.screens.clientmenu.MelonClientMenuScreen;
import wtf.melonthedev.melonclient.utils.ClientUtils;

import java.util.function.Supplier;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {

    @Shadow @Final private static Component RETURN_TO_GAME;

    @Shadow protected abstract Button openScreenButton(Component component, Supplier<Screen> supplier);

    @Shadow @Final private static Component ADVANCEMENTS;

    @Shadow @Final private static Component STATS;

    @Shadow @Final private static Component SERVER_LINKS;

    @Shadow @Final private static Component OPTIONS;

    @Shadow @Final private static Component SHARE_TO_LAN;

    @Shadow @Final private static Component PLAYER_REPORTING;

    @Shadow @Final private static Component RETURN_TO_MENU;

    @Shadow @Nullable private Button disconnectButton;

    @Shadow protected abstract void onDisconnect();

    protected PauseScreenMixin(Component component) {
        super(component);
    }


    /*@Redirect(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
    public <T extends LayoutElement> T addChildRedirect(GridLayout.RowHelper instance, T layoutElement) {
        if (!(layoutElement instanceof Button button)) return instance.addChild(layoutElement);

        ClientUtils.logDev("Created Pausescreen Button: " + button.getMessage().getString());


        // Replacing buttons with custom ones
        if (button.getMessage().equals(Component.translatable("menu.sendFeedback"))
            || button.getMessage().equals(Component.translatable("menu.feedback"))) {
            return (T) instance.addChild(melonClientButton);
        } else if (button.getMessage().equals(Component.translatable("menu.reportBugs"))) {
            //return instance.addChild(this.openScreenButton(FEEDBACK_SUBSCREEN, () -> new PauseScreen.FeedbackSubScreen(this)));
            return instance.addChild(layoutElement);
        } else if (button.getMessage().equals(Component.translatable("menu.playerReporting"))) {
            Button multiplayerButton = Button.builder(Component.literal("Multiplayer"), (p_96333_) -> this.minecraft.setScreen(new MelonClientMultiplayerScreen())).bounds(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20).build();
            return (T) instance.addChild(multiplayerButton);
        } else if (button.getMessage().equals(Component.translatable("gui.stats"))) {
            instance.addChild(layoutElement);
            return (T) instance.addChild(melonClientButton, 2);
        } else {
            return instance.addChild(layoutElement);
        }
    }*/
    /**
     * @author Melonthedev
     * @reason Render Custom Pause Screen
     * Feedback & Bug Report Buttons -> MelonClient Multiplayer Buttons
     * Open To Lan (Singleplayer)/Server Links (When empty -> player reporting button)(Multiplayer)
     */
    @Overwrite
    private void createPauseMenu() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);

        rowHelper.addChild(Button.builder(RETURN_TO_GAME, (button) -> {
            this.minecraft.setScreen((Screen)null);
            this.minecraft.mouseHandler.grabMouse();
            Client.getInstance().startIngame(false);
        }).width(204).build(), 2, gridLayout.newCellSettings().paddingTop(50));

        rowHelper.addChild(this.openScreenButton(ADVANCEMENTS, () -> {
            return new AdvancementsScreen(this.minecraft.player.connection.getAdvancements(), this);
        }));
        rowHelper.addChild(this.openScreenButton(STATS, () -> {
            return new StatsScreen(this, this.minecraft.player.getStats());
        }));

        rowHelper.addChild(this.openScreenButton(Component.literal("MelonClient"), () -> {
            return new MelonClientMenuScreen(this);
        }));

        rowHelper.addChild(this.openScreenButton(Component.literal("Multiplayer"), () -> {
            return new MelonClientMultiplayerScreen(this);
        }));

        rowHelper.addChild(this.openScreenButton(OPTIONS, () -> {
            return new OptionsScreen(this, this.minecraft.options);
        }));
        if (this.minecraft.hasSingleplayerServer() && !this.minecraft.getSingleplayerServer().isPublished()) {
            rowHelper.addChild(this.openScreenButton(SHARE_TO_LAN, () -> {
                return new ShareToLanScreen(this);
            }));
        } else {
            ServerLinks serverLinks = this.minecraft.player.connection.serverLinks();
            if (serverLinks.isEmpty()) {
                rowHelper.addChild(this.openScreenButton(PLAYER_REPORTING, () -> {
                    return new SocialInteractionsScreen(this);
                }));
            } else {
                rowHelper.addChild(this.openScreenButton(SERVER_LINKS, () -> {
                    return new ServerLinksScreen(this, serverLinks);
                }));
            }
        }

        Component component = this.minecraft.isLocalServer() ? RETURN_TO_MENU : CommonComponents.GUI_DISCONNECT;
        this.disconnectButton = (Button)rowHelper.addChild(Button.builder(component, (button) -> {
            button.active = false;
            this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::onDisconnect, true);
        }).width(204).build(), 2);
        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, 0, this.width, this.height, 0.5F, 0.25F);
        gridLayout.visitWidgets(this::addRenderableWidget);
    }


}
