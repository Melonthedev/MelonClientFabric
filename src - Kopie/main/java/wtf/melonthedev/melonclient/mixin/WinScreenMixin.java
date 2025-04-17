package wtf.melonthedev.melonclient.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.melonthedev.melonclient.Client;
import wtf.melonthedev.melonclient.utils.RenderUtil;

import java.util.Objects;

@Mixin(WinScreen.class)
public abstract class WinScreenMixin {

    @Shadow protected abstract void addEmptyLine();

    @Shadow protected abstract void addCreditsLine(Component component, boolean bl);

    @Inject(method = "addCreditsFile", at = @At("HEAD"))
    public void addCreditsFileInject(CallbackInfo info) {
        /*Component SECTION_HEADING = Component.literal("============").withStyle(ChatFormatting.WHITE);
        addCreditsLine(SECTION_HEADING, true);
        addCreditsLine(Component.literal("Melon Client").withStyle(ChatFormatting.YELLOW), true);
        addCreditsLine(SECTION_HEADING, true);
        addEmptyLine();
        addEmptyLine();
        addCreditsLine(Component.literal("Client Developer").withStyle(ChatFormatting.GRAY), false);
        addCreditsLine(Component.literal("           ").append("Melonthedev").withStyle(ChatFormatting.WHITE), false);
        addEmptyLine();
        addEmptyLine();
        addCreditsLine(Component.literal("Design").withStyle(ChatFormatting.GRAY), false);
        addCreditsLine(Component.literal("           ").append("TheJackPlay").withStyle(ChatFormatting.WHITE), false);
        addEmptyLine();
        addEmptyLine();
        addCreditsLine(Component.literal("Web Developer").withStyle(ChatFormatting.GRAY), false);
        addCreditsLine(Component.literal("           ").append("Melonthedev").withStyle(ChatFormatting.WHITE), false);
        addEmptyLine();
        addEmptyLine();
        addCreditsLine(Component.literal("With criticism from").withStyle(ChatFormatting.GRAY), false);
        addCreditsLine(Component.literal("           ").append("Jonbadon").withStyle(ChatFormatting.WHITE), false);
        addEmptyLine();
        addEmptyLine();
        addCreditsLine(Component.literal("Beta Tester").withStyle(ChatFormatting.GRAY), false);
        addCreditsLine(Component.literal("           ").append("Melonthedev").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("Retro_box").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("stebadon").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("Jonbadon").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("Flipflop").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("J4ck").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("EL_Crafter").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("Chucki").withStyle(ChatFormatting.WHITE), false);
        addEmptyLine();
        addEmptyLine();
        addCreditsLine(Component.literal("Information and inspiration").withStyle(ChatFormatting.GRAY), false);
        addCreditsLine(Component.literal("           ").append("MCP Reborn").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("Playwo").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("Eric Golde").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("NoRisk Client").withStyle(ChatFormatting.WHITE), false);
        addCreditsLine(Component.literal("           ").append("The Forge Forum").withStyle(ChatFormatting.WHITE), false);
        addEmptyLine();
        addEmptyLine();*/
        for (Component line : Client.getInstance().credits) {
            if (Objects.equals(line, Component.empty())) {
                addEmptyLine();
                continue;
            }
            boolean centered = line.getString().contains("!C!");
            addCreditsLine(centered ? Component.literal(line.getString().replaceAll("!C!", "")).withStyle(line.getStyle()) : line, centered);
        }
        addEmptyLine();
        addEmptyLine();
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void renderInject(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo info) {
        int x = ((WinScreen)(Object)this).width / 2 - 137;
        int y = ((WinScreen)(Object)this).height + 50;
        RenderUtil.drawMelonClientLogo(guiGraphics, x, y);
    }

}

