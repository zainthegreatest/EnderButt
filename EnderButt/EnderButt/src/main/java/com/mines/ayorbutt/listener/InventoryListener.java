package com.mines.ayorbutt.listener;

import com.mines.ayorbutt.pearl.PearlManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    private final PearlManager pearlManager;

    public InventoryListener(PearlManager pearlManager) {
        this.pearlManager = pearlManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack current = event.getCurrentItem();
        if (pearlManager.isAyorButtPearl(current)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (pearlManager.isAyorButtPearl(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }
}