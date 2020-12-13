package me.rerere.fakesnow.config;

import lombok.Getter;
import me.rerere.fakesnow.FakeSnow;

@Getter
public class ConfigManager {
    private DataConfig dataConfig = new DataConfig();

    public void load(){
        if(!FakeSnow.getInstance().getDataFolder().exists()){
            FakeSnow.getInstance().getDataFolder().mkdirs();
        }
        dataConfig.load();
    }
}
