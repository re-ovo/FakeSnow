package me.rerere.fakesnow.util;

public class Maths {
    private static final int e = (int)Math.round(Math.log(16.0D) / Math.log(2.0D)) - 2;
    private static final int f = (int)Math.round(Math.log(256.0D) / Math.log(2.0D)) - 2;
    public static final int a;
    public static final int b;
    public static final int c;
    static {
        a = 1 << e + e + f;
        b = (1 << e) - 1;
        c = (1 << f) - 1;
    }

    public static int biomeIndex(int i, int j, int k){
        int l = i & b;
        int i1 = clamp(j, 0, c);
        int j1 = k & b;
        return i1 << e + e | j1 << e | l;
    }

    private static int clamp(int var0, int var1, int var2) {
        if (var0 < var1) {
            return var1;
        } else {
            return Math.min(var0, var2);
        }
    }
}
