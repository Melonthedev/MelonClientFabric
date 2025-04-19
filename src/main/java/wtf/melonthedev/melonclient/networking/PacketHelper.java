package wtf.melonthedev.melonclient.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;

public class PacketHelper {


    public static void sendPacket(Packet<?> packet) {
        Minecraft.getInstance().getConnection().send(packet);
    }


}