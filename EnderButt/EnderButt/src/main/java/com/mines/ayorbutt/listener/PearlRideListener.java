package com.mines.ayorbutt.listener;

import com.mines.ayorbutt.config.ConfigManager;
import com.mines.ayorbutt.config.MessageManager;
import com.mines.ayorbutt.pearl.PearlManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class PearlRideListener implements Listener {

    private final PearlManager pearlManager;
    private final MessageManager messageManager;
    private final ConfigManager configManager;

    public PearlRideListener(PearlManager pearlManager, MessageManager messageManager, ConfigManager configManager) {
        this.pearlManager = pearlManager;
        this.messageManager = messageManager;
        this.configManager = configManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getItem() == null || event.getItem().getType() != Material.ENDER_PEARL) return;
        if (!pearlManager.isAyorButtPearl(event.getItem())) return;

        event.setCancelled(true);
        Player player = event.getPlayer();

        pearlManager.startRide(player);
        pearlManager.givePearl(player);

        String msg = messageManager.get("ride-start");
        if (msg != null && !msg.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}