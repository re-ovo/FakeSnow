package me.rerere.fakesnow.nms.versions.v1_12_r1;

import com.comphenix.protocol.events.PacketContainer;
import me.rerere.fakesnow.nms.BiomeBridge;
import me.rerere.fakesnow.nms.NMSVisitor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.function.Consumer;

public class NMS_V1_12_R1 implements NMSVisitor {
    @Override
    public void modifyChunkPacket(Player player, PacketContainer packet, Consumer<BiomeBridge> biomeBridgeConsumer) {
        boolean hasBiomeData = packet.getBooleans().readSafely(0);
        if(hasBiomeData){
            int chunkX = packet.getIntegers().readSafely(0);
            int chunkZ = packet.getIntegers().readSafely(1);

            byte[] data = packet.getByteArrays().readSafely(0);
            byte[] biomeData = Arrays.copyOfRange(data, data.length - 256, data.length);
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
                    biomeData[(z & 15) << 4 | x & 15] = (byte) 12;
                }

                @Override
                public void setBiome(int x, int y, int z) {
                    this.setBiome(x, z);
                }
            });
            int startIndex = data.length - 256;
            System.arraycopy(biomeData, 0, data, startIndex, biomeData.length);
        }
    }
}
