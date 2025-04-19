package wtf.melonthedev.melonclient.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import wtf.melonthedev.melonclient.modengine.rendering.ScreenPosition;

public class DefaultValues {

    public static ScreenPosition defaultPosArmorStatus = new ScreenPosition(892, 439);
    public static ScreenPosition defaultPosClock = new ScreenPosition(5, 80);
    public static ScreenPosition defaultPosCps = new ScreenPosition(5, 35);
    public static ScreenPosition defaultPosDate = new ScreenPosition(5, 110);
    public static ScreenPosition defaultPosFacingDirection = new ScreenPosition(5, 20);
    public static ScreenPosition defaultPosFps = new ScreenPosition(5, 65);
    public static ScreenPosition defaultPosPing = new ScreenPosition(5, 50);
    public static ScreenPosition defaultPosCoords = new ScreenPosition(5, 5);
    public static ScreenPosition defaultPosServer = new ScreenPosition(5, 125);
    public static ScreenPosition defaultPosPotionStatus = new ScreenPosition(5, 200);

    public static Component[] credits = new Component[] {
            Component.literal("!C!============").withStyle(ChatFormatting.WHITE),
            Component.literal("!C!Melon Client").withStyle(ChatFormatting.YELLOW),
            Component.literal("!C!============").withStyle(ChatFormatting.WHITE),
            Component.empty(),
            Component.empty(),
            Component.literal("Client Developer").withStyle(ChatFormatting.GRAY),
            Component.literal("           ").append("Melonthedev").withStyle(ChatFormatting.WHITE),
            Component.empty(),
            Component.empty(),
            Component.literal("Design").withStyle(ChatFormatting.GRAY),
            Component.literal("           ").append("TheJackPlay").withStyle(ChatFormatting.WHITE),
            Component.empty(),
            Component.empty(),
            Component.literal("Web Developer").withStyle(ChatFormatting.GRAY),
            Component.literal("           ").append("Melonthedev").withStyle(ChatFormatting.WHITE),
            Component.empty(),
            Component.empty(),
            Component.literal("With criticism from").withStyle(ChatFormatting.GRAY),
            Component.literal("           ").append("Jonbadon").withStyle(ChatFormatting.WHITE),
            Component.empty(),
            Component.empty(),
            Component.literal("Beta Tester").withStyle(ChatFormatting.GRAY),
            Component.literal("           ").append("Melonthedev").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("Retro_box").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("stebadon").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("Jonbadon").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("Flipflop").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("J4ck").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("EL_Crafter").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("Chucki").withStyle(ChatFormatting.WHITE),
            Component.empty(),
            Component.empty(),
            Component.literal("Information and inspiration").withStyle(ChatFormatting.GRAY),
            Component.literal("           ").append("MCP Reborn").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("Playwo").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("Eric Golde").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("NoRisk Client").withStyle(ChatFormatting.WHITE),
            Component.literal("           ").append("The Forge Forum").withStyle(ChatFormatting.WHITE),
            Component.empty(),
            Component.empty()
    };

}
