package me.rerere.fakesnow.config;

import lombok.Getter;
import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.util.YamlUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataConfig {
    private File file;
    @Getter
    private YamlConfiguration configuration;

    public void load(){
        this.file = new File(FakeSnow.getInstance().getDataFolder(),"data.yml");
        if(!file.exists()){
            FakeSnow.getInstance().saveResource("data.yml", false);
        }
        this.configuration = YamlUtil.load(file);
    }

    public void save(){
        try {
            this.configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            FakeSnow.getInstance().getLogger().info("Failed to save the region data");
        }
    }
}
