package me.rerere.fakesnow.manager;

import lombok.Getter;
import lombok.Setter;
import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.config.DataConfig;
import me.rerere.fakesnow.nms.BiomeBridge;
import me.rerere.fakesnow.util.VersionUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SnowController {
    @Getter
    private final Set<Arena> arenas = new HashSet<>();
    @Getter
    @Setter
    private Vector pointA, pointB;
    @Getter
    @Setter
    private String world;

    public SnowController() {
        YamlConfiguration configuration = FakeSnow.getInstance().getConfigManager().getDataConfig().getConfiguration();
        // snow worlds
        List<String> worlds = configuration.getStringList("snow_worlds");
        for (String w : worlds) {
            arenas.add(new Arena(w, w, null, null));
            FakeSnow.getInstance().getLogger().info("- Add Snow World: " + w);
        }
        // snow arena
        ConfigurationSection section = configuration.getConfigurationSection("regions");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                String world = section.getString(key + ".world");
                Vector pointOne = section.getVector(key + ".pointOne");
                Vector pointTwo = section.getVector(key + ".pointTwo");
                if (pointOne == null || pointTwo == null) {
                    FakeSnow.getInstance().getLogger().warning("Region " + key + " 's data was broken, please delete it from data.yml");
                    continue;
                }
                arenas.add(new Arena(key, world, pointOne, pointTwo));
            }
        }
        // done
        FakeSnow.getInstance().getLogger().info("Loaded " + arenas.size() + " worlds & regions");
    }

    // TODO
    public void handle(BiomeBridge biomeBridge) {
        World world = biomeBridge.getPlayer().getWorld();
        if (isInSnowWorld(world)) {
            // All Snow
            if (VersionUtil.getVersion() < 15) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        biomeBridge.setBiome(x, z);
                    }
                }
            } else {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = 0; y < 256; y++) {
                            biomeBridge.setBiome(x, y, z);
                        }
                    }
                }
            }
        }else if(shouldSnow(world, biomeBridge.getChunkX(), biomeBridge.getChunkZ())){
            if (VersionUtil.getVersion() < 15) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        biomeBridge.setBiome(x, z);
                    }
                }
            } else {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = 0; y < 256; y++) {
                            biomeBridge.setBiome(x, y, z);
                        }
                    }
                }
            }
        }
    }

    public boolean shouldForceSnow(Player player){
        Location location = player.getLocation();
        World world = location.getWorld();
        for(Arena arena : arenas){
            if(arena.isWholeWorld()){
                if(world.getName().equalsIgnoreCase(arena.getWorld())){
                    return true;
                }
            }else {
                if(world.getName().equalsIgnoreCase(arena.getWorld())){
                    if(arena.isIn(location)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean shouldForceSnow(Player player, Location location){
        // Location location = player.getLocation();
        World world = location.getWorld();
        for(Arena arena : arenas){
            if(arena.isWholeWorld()){
                if(world.getName().equalsIgnoreCase(arena.getWorld())){
                    return true;
                }
            }else {
                if(world.getName().equalsIgnoreCase(arena.getWorld())){
                    if(arena.isIn(location)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean shouldSnow(World world, int chunkX, int chunkZ){
        return this.arenas.stream().filter(Arena::isRegion).filter(arena -> arena.getWorld().equalsIgnoreCase(world.getName())).anyMatch(arena -> arena.isIn(chunkX, chunkZ));
    }

    public boolean isInSnowWorld(World world) {
        return this.arenas.stream().filter(Arena::isWholeWorld).anyMatch(arena -> arena.getWorld().equalsIgnoreCase(world.getName()));
    }

    public boolean isInSnowWorld(String world) {
        return this.arenas.stream().filter(Arena::isWholeWorld).anyMatch(arena -> arena.getWorld().equalsIgnoreCase(world));
    }

    public void addWorld(String world){
        this.arenas.add(new Arena(world, world, null, null));
        save();
    }

    public void save() {
        DataConfig dataConfig = FakeSnow.getInstance().getConfigManager().getDataConfig();
        YamlConfiguration configuration = dataConfig.getConfiguration();
        configuration.set("snow_worlds", null);
        configuration.set("regions", null);
        List<String> wholeWorlds = arenas.stream().filter(Arena::isWholeWorld).map(Arena::getWorld).collect(Collectors.toList());
        configuration.set("snow_worlds", wholeWorlds);
        for (Arena arena : arenas) {
            if (!arena.isWholeWorld()) {
                configuration.set("regions." + arena.getName() + ".world", arena.getWorld());
                configuration.set("regions." + arena.getName() + ".pointOne", arena.getPointOne());
                configuration.set("regions." + arena.getName() + ".pointTwo", arena.getPointTwo());
            }
        }
        dataConfig.save();
        FakeSnow.getInstance().getLogger().info("Saved data~!");
    }
}
