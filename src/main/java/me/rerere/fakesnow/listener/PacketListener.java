package me.rerere.fakesnow.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.nms.BiomeBridge;
import me.rerere.fakesnow.util.VersionUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class PacketListener extends PacketAdapter {
    private static final PacketType[] OLD_TYPES = new PacketType[]{PacketType.Play.Server.MAP_CHUNK, PacketType.Play.Server.MAP_CHUNK_BULK};
    private static final PacketType[] NEW_TYPES = new PacketType[]{PacketType.Play.Server.MAP_CHUNK};

    public PacketListener() {
        super(FakeSnow.getInstance(), VersionUtil.getVersion() == 8 ? OLD_TYPES : NEW_TYPES);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packetContainer = event.getPacket();
        PacketType type = event.getPacketType();
        Player player = event.getPlayer();
        if(type == PacketType.Play.Server.MAP_CHUNK || type == PacketType.Play.Server.MAP_CHUNK_BULK) {
            if(player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                FakeSnow.getInstance().getNmsManager().getVisitor().modifyChunkPacket(player, packetContainer, biomeBridge -> FakeSnow.getInstance().getSnowController().handle(biomeBridge));
            }
        }
    }
}
