package me.rerere.fakesnow.util;

import org.bukkit.util.Vector;

public class VecUtil {
    public static String asString(Vector vector){
        return vector.getBlockX()+","+vector.getBlockZ();
    }
}
