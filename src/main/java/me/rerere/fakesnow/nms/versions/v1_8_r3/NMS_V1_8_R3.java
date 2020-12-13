package me.rerere.fakesnow.nms.versions.v1_8_r3;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import me.rerere.fakesnow.nms.BiomeBridge;
import me.rerere.fakesnow.nms.NMSVisitor;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.function.Consumer;

public class NMS_V1_8_R3 implements NMSVisitor {
    @Override
    public void modifyChunkPacket(Player player, PacketContainer packet, Consumer<BiomeBridge> biomeBridgeConsumer) {
        if(packet.getType() == PacketType.Play.Server.MAP_CHUNK){
            PacketPlayOutMapChunk.ChunkMap chunkMap = packet.getSpecificModifier(PacketPlayOutMapChunk.ChunkMap.class).readSafely(0);
            if(chunkMap.a.length >= 49408) {
                int chunkX = packet.getIntegers().readSafely(0);
                int chunkZ = packet.getIntegers().readSafely(1);
                byte[] biomeData = Arrays.copyOfRange(chunkMap.a, chunkMap.a.length - 256, chunkMap.a.length);
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
                        biomeData[(z & 15) << 4 | x & 15] = 11;
                    }

                    @Override
                    public void setBiome(int x, int y, int z) {
                        this.setBiome(x,z);
                    }
                });
                this.modifyChunk(chunkMap, biomeData);
            }
        }else if(packet.getType() == PacketType.Play.Server.MAP_CHUNK_BULK){
            int[] chunkXArray = packet.getIntegerArrays().readSafely(0);
            int[] chunkZArray = packet.getIntegerArrays().readSafely(1);
            for(int s = 0; s < chunkXArray.length; s ++){
                int chunkX = chunkXArray[s];
                int chunkZ = chunkZArray[s];
                PacketPlayOutMapChunk.ChunkMap chunkMap = packet.getSpecificModifier(PacketPlayOutMapChunk.ChunkMap[].class).readSafely(0)[s];
                if(chunkMap.a.length >= 49408) {
                    byte[] biomeData = Arrays.copyOfRange(chunkMap.a, chunkMap.a.length - 256, chunkMap.a.length);
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
                            biomeData[(z & 15) << 4 | x & 15] = 12;
                        }

                        @Override
                        public void setBiome(int x, int y, int z) {
                            this.setBiome(x,z);
                        }
                    });
                    this.modifyChunk(chunkMap, biomeData);
                }
            }
        }
    }

    private void modifyChunk(PacketPlayOutMapChunk.ChunkMap chunkMap, byte[] newBiome){
        if(newBiome.length != 256){
            throw new IllegalArgumentException("biome size is not 256!");
        }
        if(chunkMap.a.length >= 49408){
            int startIndex = chunkMap.a.length - 256;
            for(int i = 0; i < newBiome.length; i++){
                chunkMap.a[startIndex + i] = newBiome[i];
            }
        }
    }
}
