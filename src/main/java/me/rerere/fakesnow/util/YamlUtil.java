package me.rerere.fakesnow.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class YamlUtil {
    public static YamlConfiguration load(File file) {
        YamlConfiguration yamlConfiguration = null;
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            yamlConfiguration = YamlConfiguration.loadConfiguration((reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return yamlConfiguration;
    }
}
