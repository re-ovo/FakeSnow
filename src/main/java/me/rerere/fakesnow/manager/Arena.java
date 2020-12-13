package me.rerere.fakesnow.manager;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Arena {
    private String name;
    private String world;
    private Vector pointOne, pointTwo;

    public boolean isWholeWorld(){
        return pointOne == null || pointTwo == null;
    }

    public boolean isRegion(){
        return !isWholeWorld();
    }

    public boolean isIn(int chunkX, int chunkZ){
        if(pointOne == null || pointTwo == null){
            // whole world snow
            return true;
        }
        double minX = (int)Math.min(pointOne.getX(), pointTwo.getX()) >> 4;
        double maxX = (int)Math.max(pointOne.getX(), pointTwo.getX()) >> 4;
        double minZ = (int)Math.min(pointOne.getZ(), pointTwo.getZ()) >> 4;
        double maxZ = (int)Math.max(pointOne.getZ(), pointTwo.getZ()) >> 4;

        return chunkX >= minX && chunkX <= maxX && chunkZ >= minZ && chunkZ <= maxZ;
    }

    public boolean isIn(Location location){
        if(!location.getWorld().getName().equalsIgnoreCase(world)){
            return false;
        }
        Chunk chunk = location.getChunk();
        return isIn(chunk.getX(), chunk.getZ());
    }
}
