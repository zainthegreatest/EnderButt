package com.mines.ayorbutt.command;

import com.mines.ayorbutt.AyorButtPlugin;
import com.mines.ayorbutt.config.ConfigManager;
import com.mines.ayorbutt.config.MessageManager;
import com.mines.ayorbutt.pearl.PearlManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class AyorButtCommand implements CommandExecutor {

    private final AyorButtPlugin plugin;
    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final PearlManager pearlManager;

    public AyorButtCommand(AyorButtPlugin plugin, ConfigManager configManager, MessageManager messageManager, PearlManager pearlManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.messageManager = messageManager;
        this.pearlManager = pearlManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendFancyHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("ayorbutt.reload") && !sender.hasPermission("ayorbutt.admin")) {
                sender.sendMessage(color("&c✗ &7You don't have permission to do this!"));
                return true;
            }
            plugin.reloadConfig();
            configManager.reload();
            messageManager.reload();
            pearlManager.refreshAllPlayers();
            sender.sendMessage("");
            sender.sendMessage(color("&a&l✔ &bAyorButt &7has been &areloaded &7successfully!"));
            sender.sendMessage("");
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (!sender.hasPermission("ayorbutt.info") && !sender.hasPermission("ayorbutt.admin")) {
                sender.sendMessage(color("&c✗ &7You don't have permission to do this!"));
                return true;
            }
            sendFancyInfo(sender);
            return true;
        }

        sender.sendMessage(color("&c✗ &7Unknown command! Type &b/ayorbutt help &7for help."));
        return true;
    }

    private void sendFancyHelp(CommandSender sender) {
        sender.sendMessage(color(""));
        sender.sendMessage(color("&b&l" + repeat("━", 36)));
        sender.sendMessage(color("      &d&lAyor&r&6&lButt       "));
        sender.sendMessage(color("&b&l" + repeat("━", 36)));
        sender.sendMessage(color("&7&l» &b/ayorbutt help &8- &fShow this help menu"));
        sender.sendMessage(color("&7&l» &b/ayorbutt reload &8- &fReload config/messages "));
        sender.sendMessage(color("&7&l» &b/ayorbutt info &8- &fShow plugin info/settings "));
        sender.sendMessage(color("&b&l" + repeat("━", 36)));
        sender.sendMessage(color("&7Discord: &bdiscord.gg/ayormc"));
        sender.sendMessage(color(""));
    }

    private void sendFancyInfo(CommandSender sender) {
        sender.sendMessage(color(""));
        sender.sendMessage(color("&6&l" + repeat("━", 36)));
        sender.sendMessage(color("   &b&lAyor&r&6&lButt   "));
        sender.sendMessage(color("&6&l" + repeat("━", 36)));
        sender.sendMessage(color("&d&l• &7Version: &b" + plugin.getDescription().getVersion()));
        sender.sendMessage(color("&d&l• &7Pearl Name: &f" + ChatColor.translateAlternateColorCodes('&', configManager.getPearlName())));
        sender.sendMessage(color("&d&l• &7Pearl Lore: &f" + ChatColor.translateAlternateColorCodes('&', configManager.getPearlLore())));
        sender.sendMessage(color("&d&l• &7Pearl Slot: &b" + configManager.getPearlSlot()));
        sender.sendMessage(color("&d&l• &7Trail: &b" + configManager.getTrail()));
        sender.sendMessage(color("&d&l• &7Bukkit: &b" + plugin.getServer().getBukkitVersion()));
        sender.sendMessage(color("&d&l• &7Online Mode: &cDisabled"));
        sender.sendMessage(color("&6&l" + repeat("━", 36)));
        sender.sendMessage(color("&7Enjoy your ride! &b&oStay legendary &d❤"));
        sender.sendMessage(color(""));
    }
    private String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    private String repeat(String s, int n) {
        return new String(new char[n]).replace("\0", s);
    }
}