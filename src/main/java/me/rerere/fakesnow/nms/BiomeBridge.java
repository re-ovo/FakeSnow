package me.rerere.fakesnow.nms;

import org.bukkit.entity.Player;

public interface BiomeBridge {
    int getChunkX();
    int getChunkZ();
    Player getPlayer();

    void setBiome(int x, int z);
    void setBiome(int x, int y, int z);
}
