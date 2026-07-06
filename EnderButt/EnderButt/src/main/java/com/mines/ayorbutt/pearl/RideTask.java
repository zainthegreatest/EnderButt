package com.mines.ayorbutt.pearl;

import com.mines.ayorbutt.AyorButtPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RideTask {


    private static final Map<UUID, EnderPearl> ridingPearls = new HashMap<>();
    public static void ride(final Player player, PearlManager pearlManager) {
        UUID uuid = player.getUniqueId();
        stop(player);
        final EnderPearl pearl = player.launchProjectile(EnderPearl.class);
        Bukkit.getScheduler().runTaskLater(AyorButtPlugin.getInstance(), () -> {
            if (pearl.isValid() && !pearl.isDead()) {
                pearl.setPassenger(player);
                ridingPearls.put(uuid, pearl);
            }
        }, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                EnderPearl trackedPearl = ridingPearls.get(uuid);
                if (trackedPearl == null || trackedPearl.isDead() || !trackedPearl.isValid()) {
                    ridingPearls.remove(uuid);
                    cancel();
                    return;
                }
                if (trackedPearl.getPassenger() == null || !trackedPearl.getPassenger().equals(player)) {
                    ridingPearls.remove(uuid);
                    cancel();
                    return;
                }
                Location loc = trackedPearl.getLocation();
                loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
                if (trackedPearl.isOnGround() || loc.getBlock().getType().isSolid()) {
                    stop(player, true);
                    cancel();
                }
            }
        }.runTaskTimer(AyorButtPlugin.getInstance(), 2L, 1L);
    }

    public static void stop(Player player) {
        stop(player, false);
    }

    public static void stop(Player player, boolean landed) {
        UUID uuid = player.getUniqueId();
        EnderPearl pearl = ridingPearls.get(uuid);
        if (pearl != null && pearl.isValid()) {
            if (pearl.getPassenger() != null && pearl.getPassenger().equals(player)) {
                pearl.eject();
            }
            pearl.remove();
        }
        ridingPearls.remove(uuid);

        if (landed) {
            Location landLoc = player.getLocation();
            landLoc.getWorld().playEffect(landLoc, Effect.EXPLOSION_LARGE, 0);
            landLoc.getWorld().playEffect(landLoc, Effect.MOBSPAWNER_FLAMES, 0);
            landLoc.getWorld().playSound(landLoc, Sound.EXPLODE, 1.0F, 1.0F);

            player.setVelocity(new Vector(0, -1.5, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 1, true, false));
        }
    }
    public static boolean isRiding(Player player) {
        EnderPearl pearl = ridingPearls.get(player.getUniqueId());
        return pearl != null && pearl.isValid() && pearl.getPassenger() != null && pearl.getPassenger().equals(player);
    }
}