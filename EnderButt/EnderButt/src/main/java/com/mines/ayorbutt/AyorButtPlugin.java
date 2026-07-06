package com.mines.ayorbutt;

import com.mines.ayorbutt.command.AyorButtCommand;
import com.mines.ayorbutt.config.ConfigManager;
import com.mines.ayorbutt.config.MessageManager;
import com.mines.ayorbutt.listener.InventoryListener;
import com.mines.ayorbutt.listener.PlayerListener;
import com.mines.ayorbutt.listener.PearlRideListener;
import com.mines.ayorbutt.pearl.PearlManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AyorButtPlugin extends JavaPlugin {

    private static AyorButtPlugin instance;
    private ConfigManager configManager;
    private MessageManager messageManager;
    private PearlManager pearlManager;

    public static AyorButtPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        configManager = new ConfigManager(this);
        messageManager = new MessageManager(this);
        pearlManager = new PearlManager(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(pearlManager), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(pearlManager), this);
        getServer().getPluginManager().registerEvents(new PearlRideListener(pearlManager, messageManager, configManager), this);

        getCommand("ayorbutt").setExecutor(new AyorButtCommand(this, configManager, messageManager, pearlManager));

        getLogger().info("    §b                      ____        _   _   ");
        getLogger().info("    §b/\\                   |  _ \\      | | | |  ");
        getLogger().info("   §b/  \\  _   _  ___  _ __| |_) |_   _| |_| |_ ");
        getLogger().info("  §b/ /\\ \\| | | |/ _ \\| '__|  _ <| | | | __| __|");
        getLogger().info(" §b/ ____ \\ |_| | (_) | |  | |_) | |_| | |_| |_ ");
        getLogger().info("§b/_/    \\_\\__, |\\___/|_|  |____/ \\__,_|\\__|\\__|");
        getLogger().info("§b          __/ |                               ");
        getLogger().info("§b         |___/                                ");
        getLogger().info("§b[AyorButt] §7by MinesInfinite");
        getLogger().info("§b[AyorButt] §7Version: §f" + getDescription().getVersion());
        getLogger().info("§b[AyorButt] §7Bukkit: §f" + getServer().getBukkitVersion());
        getLogger().info("§b[AyorButt] §7Online Mode: §cDisabled");
        getLogger().info("§b[AyorButt] §aPlugin enabled successfully!");
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            getLogger().info("§b[AyorButt] §eLuckPerms hooked successfully!");
        }
        getLogger().info(" ");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("§b[AyorButt] §aAyorButt Disabled successfully");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public PearlManager getPearlManager() {
        return pearlManager;
    }
}