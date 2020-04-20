package me.badbones69.crazyenchantments.multisupport.anticheats;

import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AACSupport implements Listener {
    
    private static List<UUID> exempted = new ArrayList<>();
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("CrazyEnchantments");
    
    public static void exemptPlayer(Player player) {
        if (player != null && !exempted.contains(player.getUniqueId())) {
            exempted.add(player.getUniqueId());
        }
    }
    
    public static void unexemptPlayer(Player player) {
        if (player != null) {
            exempted.remove(player.getUniqueId());
        }
    }
    
    public static boolean isExempted(Player player) {
        return player != null && exempted.contains(player.getUniqueId());
    }
    
    public static void exemptPlayerTime(Player player) {
        exemptPlayer(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                unexemptPlayer(player);
            }
        }.runTaskLaterAsynchronously(plugin, 100);
    }
    
    @EventHandler
    public void onViolation(PlayerViolationEvent e) {
        if (isExempted(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
}