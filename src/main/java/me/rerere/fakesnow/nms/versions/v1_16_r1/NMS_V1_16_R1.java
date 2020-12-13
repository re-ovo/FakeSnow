package me.rerere.fakesnow.nms.versions.v1_16_r1;

import com.comphenix.protocol.events.PacketContainer;
import me.rerere.fakesnow.nms.BiomeBridge;
import me.rerere.fakesnow.nms.NMSVisitor;
import net.minecraft.server.v1_16_R1.BiomeBase;
import net.minecraft.server.v1_16_R1.BiomeStorage;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_16_R1.block.CraftBlock;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class NMS_V1_16_R1 implements NMSVisitor {
    private static final BiomeBase BIOME_BASE = CraftBlock.biomeToBiomeBase(Biome.FROZEN_RIVER);

    @Override
    public void modifyChunkPacket(Player player, PacketContainer packet, Consumer<BiomeBridge> biomeBridgeConsumer) {
        boolean hasBiome = packet.getBooleans().readSafely(0);
        if(hasBiome){
            int chunkX = packet.getIntegers().readSafely(0);
            int chunkZ = packet.getIntegers().readSafely(1);
            BiomeStorage biomeStorage = packet.getSpecificModifier(BiomeStorage.class).readSafely(0);
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
                    biomeStorage.setBiome(x >> 2, y >> 2, z >> 2, BIOME_BASE);
                }
            });
        }
    }
}
