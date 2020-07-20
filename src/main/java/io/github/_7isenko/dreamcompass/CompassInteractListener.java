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
import org.bukkit.inventory.ItemStack;

public class CompassInteractListener implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEvent e) {
        if (DreamCompass.hunters.containsKey(e.getPlayer())) {
            Player player = e.getPlayer();
            Player target;
            ItemStack compass;
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.COMPASS) {
                target = Bukkit.getPlayer(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                compass = player.getInventory().getItemInMainHand();
            } else if (e.getPlayer().getInventory().getItemInOffHand().getType() == Material.COMPASS) {
                target = Bukkit.getPlayer(player.getInventory().getItemInOffHand().getItemMeta().getDisplayName());
                compass = player.getInventory().getItemInOffHand();
            } else return;
            CompassHelper.setTarget(compass, target);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&bCompass is pointing to " + compass.getItemMeta().getDisplayName())));
        }
    }
}
