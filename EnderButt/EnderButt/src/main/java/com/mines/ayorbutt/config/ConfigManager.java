package com.mines.ayorbutt.config;

import com.mines.ayorbutt.AyorButtPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final AyorButtPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(AyorButtPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public String getPearlName() {
        return config.getString("pearl-name", "&bAyor&6Butt");
    }

    public String getPearlLore() {
        return config.getString("pearl-lore", "&7Right click to ride the pearl!");
    }

    public int getPearlSlot() {
        return config.getInt("pearl-slot", 0);
    }

    public String getTrail() {
        return config.getString("trail", "FIRE").toUpperCase();
    }
}