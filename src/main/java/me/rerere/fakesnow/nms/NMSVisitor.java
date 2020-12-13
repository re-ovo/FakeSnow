package me.rerere.fakesnow.nms;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface NMSVisitor {
    void modifyChunkPacket(Player player, PacketContainer packet, Consumer<BiomeBridge> biomeBridgeConsumer);
}
