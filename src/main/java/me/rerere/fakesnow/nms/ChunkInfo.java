package me.rerere.fakesnow.nms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.World;

@AllArgsConstructor
@Getter
public class ChunkInfo {
    private World world;
    private int chunkX,chunkZ;
    private byte[] biomes;
}
