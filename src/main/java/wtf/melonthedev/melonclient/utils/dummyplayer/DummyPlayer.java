package wtf.melonthedev.melonclient.utils.dummyplayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.ServerLinks;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import wtf.melonthedev.melonclient.Client;

import java.util.ArrayList;
import java.util.HashMap;

public class DummyPlayer extends LocalPlayer {
    public DummyPlayer(Screen screen, ClientLevel level, GameProfile profile) {
        super(Client.getMinecraft(), level, new ClientPacketListener(Client.getMinecraft(),
                new Connection(PacketFlow.CLIENTBOUND),
                new CommonListenerCookie(profile,
                        null,
                        RegistryAccess.Frozen.EMPTY,
                        FeatureFlagSet.of(FeatureFlags.VANILLA),
                        "",
                        new ServerData("", "", ServerData.Type.OTHER),
                        screen,
                        null,
                        null,
                        false,
                        new HashMap<String, String>(),
                        ServerLinks.EMPTY
                        )), new StatsCounter(), new ClientRecipeBook(), false, false);
    }





}
