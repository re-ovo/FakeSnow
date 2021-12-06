package me.rerere.fakesnow.nms.versions.v1_17_r1;

import com.comphenix.protocol.events.PacketContainer;
import me.rerere.fakesnow.nms.BiomeBridge;
import me.rerere.fakesnow.nms.NMSVisitor;
import me.rerere.fakesnow.util.Maths;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class NMS_V1_17_R1 implements NMSVisitor {
    @Override
    public void modifyChunkPacket(Player player, PacketContainer packet, Consumer<BiomeBridge> biomeBridgeConsumer) {
        int chunkX = packet.getIntegers().readSafely(0);
        int chunkZ = packet.getIntegers().readSafely(1);
        int[] data = packet.getIntegerArrays().readSafely(0);
        biomeBridgeConsumer.accept(new BiomeBridge() {
            @Override
            public int getChunkX() {
                return chunkX;
            }

            @Override
            public int getChunkZ() {
                return chunkZ;
            }

            @Override
            public Player getPlayer() {
                return player;
            }

            @Override
            public void setBiome(int x, int z) {
                throw new UnsupportedOperationException("Not support 2D Biome Operations in this version!");
            }

            @Override
            public void setBiome(int x, int y, int z) {
                data[Maths.biomeIndex(x >> 2, y>>2, z>>2)] = 11;
            }
        });
    }
}
