package io.github._7isenko.dreamcompass;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CompassInteractListener implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEvent e) {
        if (DreamCompass.hunters.containsKey(e.getPlayer()) && (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.COMPASS || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.COMPASS)) {
            Player target = Bukkit.getPlayer(DreamCompass.hunters.get(e.getPlayer()));
            if (target != null) {
                e.getPlayer().setCompassTarget(target.getLocation());
                e.getPlayer().sendMessage(ChatColor.GOLD + "Compass is pointing to " + DreamCompass.hunters.get(e.getPlayer()));
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lY: &b" + target.getLocation().getBlockY())));
            } else {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPlayer &4&l" + DreamCompass.hunters.get(e.getPlayer()) + "&c&l is offline"));
            }
        }
    }
}