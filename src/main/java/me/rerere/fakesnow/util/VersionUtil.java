package me.rerere.fakesnow.util;

import org.bukkit.Bukkit;

public class VersionUtil {
    private static final int version;

    static {
        String v = getNMSVersion();
        version = Integer.parseInt(v.split("_")[1]);
    }

    private static String getNMSVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static int getVersion() {
        return version;
    }
}
