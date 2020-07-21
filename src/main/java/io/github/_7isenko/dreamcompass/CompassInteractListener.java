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
    @SuppressWarnings({"ConstantConditions", "unused"})
    public void onEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack compass = getCompass(player);
        if (compass == null) return;
        if (!compass.getItemMeta().hasDisplayName()) return;
        String name = compass.getItemMeta().getDisplayName();
        Player target = Bukkit.getPlayer(name);
        if (target != null) {
            CompassHelper.setTarget(compass, target);
            sendToActionBar(player, "&3&bCompass is pointing to " + name);
        } else sendToActionBar(player, "&3&b" + name + " is offline");

    }

    private ItemStack getCompass(Player player) {
        ItemStack compass = null;
        if (player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            compass = player.getInventory().getItemInMainHand();
        } else if (player.getInventory().getItemInOffHand().getType() == Material.COMPASS) {
            compass = player.getInventory().getItemInOffHand();
        }
        return compass;
    }

    private void sendToActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
    }
}
