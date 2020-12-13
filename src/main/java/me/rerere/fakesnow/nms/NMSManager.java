package me.rerere.fakesnow.nms;

import lombok.Getter;
import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.nms.versions.v1_12_r1.NMS_V1_12_R1;
import me.rerere.fakesnow.nms.versions.v1_15_r1.NMS_V1_15_R1;
import me.rerere.fakesnow.nms.versions.v1_16_r1.NMS_V1_16_R1;
import me.rerere.fakesnow.nms.versions.v1_16_r2.NMS_V1_16_R2;
import me.rerere.fakesnow.nms.versions.v1_16_r3.NMS_V1_16_R3;
import me.rerere.fakesnow.nms.versions.v1_8_r3.NMS_V1_8_R3;
import org.bukkit.Bukkit;

public class NMSManager {
    @Getter
    private final NMSVisitor visitor;

    public NMSManager() {
        String nms = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        switch (nms) {
            case "v1_8_R3":
                this.visitor = new NMS_V1_8_R3();
                break;
            case "v1_12_R1":
                this.visitor = new NMS_V1_12_R1();
                break;
            case "v1_15_R1":
                this.visitor = new NMS_V1_15_R1();
                break;
            case "v1_16_R1":
                this.visitor = new NMS_V1_16_R1();
                break;
            case "v1_16_R2":
                this.visitor = new NMS_V1_16_R2();
                break;
            case "v1_16_R3":
                this.visitor = new NMS_V1_16_R3();
                break;
            default:
                throw new UnknowMinecraftVersionException("Unsupported Minecraft Server Version: " + nms);
        }
        FakeSnow.getInstance().getLogger().info("Loaded NMSVisitor for: "+nms);
    }
}
