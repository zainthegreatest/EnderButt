package com.mines.ayorbutt.listener;

import com.mines.ayorbutt.pearl.PearlManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private final PearlManager pearlManager;

    public PlayerListener(PearlManager pearlManager) {
        this.pearlManager = pearlManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        pearlManager.givePearl(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Bukkit.getScheduler().runTaskLater(pearlManager.getPlugin(), () -> {
            pearlManager.givePearl(event.getPlayer());
        }, 2L);
    }
}