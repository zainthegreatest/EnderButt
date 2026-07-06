package com.mines.ayorbutt.pearl;

import com.mines.ayorbutt.AyorButtPlugin;
import com.mines.ayorbutt.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class PearlManager {

    private final AyorButtPlugin plugin;
    private final ConfigManager configManager;

    public PearlManager(AyorButtPlugin plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public AyorButtPlugin getPlugin() {
        return plugin;
    }

    public void givePearl(Player player) {
        removeAllPearls(player);

        ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = pearl.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', configManager.getPearlName()));
        meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', configManager.getPearlLore())));
        pearl.setItemMeta(meta);

        player.getInventory().setItem(configManager.getPearlSlot(), pearl);
    }

    public boolean isAyorButtPearl(ItemStack item) {
        if (item == null || item.getType() != Material.ENDER_PEARL) return false;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return false;
        String expected = ChatColor.translateAlternateColorCodes('&', configManager.getPearlName());
        return item.getItemMeta().getDisplayName().equals(expected);
    }

    public void removeAllPearls(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (isAyorButtPearl(item)) {
                player.getInventory().setItem(i, null);
            }
        }
    }

    public void refreshAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            givePearl(player);
        }
    }

    public void startRide(Player player) {
        RideTask.ride(player, this);
    }

    public void stopRide(Player player) {
        RideTask.stop(player);
    }

    public boolean isRiding(Player player) {
        return RideTask.isRiding(player);
    }
}