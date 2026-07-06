package com.mines.ayorbutt.config;

import com.mines.ayorbutt.AyorButtPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessageManager {
    private final AyorButtPlugin plugin;
    private Map<String, String> messages = new HashMap<>();
    private File file;
    private YamlConfiguration config;

    public MessageManager(AyorButtPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            messages.put(key, config.getString(key));
        }
    }

    public void reload() {
        messages.clear();
        load();
    }

    public String get(String key) {
        return messages.getOrDefault(key, null);
    }
}