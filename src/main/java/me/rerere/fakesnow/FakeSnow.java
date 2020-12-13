package me.rerere.fakesnow;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import me.rerere.fakesnow.commands.FakeSnowCommand;
import me.rerere.fakesnow.config.ConfigManager;
import me.rerere.fakesnow.listener.EventListener;
import me.rerere.fakesnow.listener.PacketListener;
import me.rerere.fakesnow.manager.SnowController;
import me.rerere.fakesnow.nms.NMSManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FakeSnow extends JavaPlugin {
    @Getter
    private static FakeSnow instance;
    @Getter
    private ConfigManager configManager;
    @Getter
    private NMSManager nmsManager;
    @Getter
    private SnowController snowController;

    @Override
    public void onEnable() {
        instance = this;
        this.getLogger().info("Start loading FakeSnow..");

        // config
        this.configManager = new ConfigManager();
        this.configManager.load();
        this.getLogger().info("Loaded configuration files");

        // controller
        this.snowController = new SnowController();

        // NMS
        nmsManager = new NMSManager();

        // Packet Listener
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener());
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        this.getLogger().info("Registered event listeners");

        // command
        FakeSnowCommand fakeSnowCommand = new FakeSnowCommand();
        Bukkit.getPluginCommand("fakesnow").setExecutor(fakeSnowCommand);
        Bukkit.getPluginCommand("fakesnow").setTabCompleter(fakeSnowCommand);
        this.getLogger().info("Registered the command");

        this.getLogger().info("Loaded FakeSnow ~");
    }

    @Override
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }
}
