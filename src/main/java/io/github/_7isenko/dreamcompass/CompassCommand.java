package io.github._7isenko.dreamcompass;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        if (args.length == 0) {
            sendMessageWithColors(sender, "&c&lUse /compass <player>");
            return true;
        }

        if (args.length == 1) {
            Player player = (Player) sender;
            String name = args[0];
            Player target = Bukkit.getPlayer(name);
            if (target != null) {
                DreamCompass.hunters.put(player, name);
                ItemStack compass = new ItemStack(Material.COMPASS);
                CompassMeta meta = (CompassMeta) compass.getItemMeta();
                meta.setLodestone(target.getLocation());
                meta.setLodestoneTracked(false);
                meta.setDisplayName(target.getName());
                compass.setItemMeta(meta);
                player.getInventory().addItem(compass);
                sender.sendMessage(ChatColor.GOLD + "Compass is pointing to " + name);
                return true;
            }
            sendMessageWithColors(sender, "&c&lPlayer &4&l" + name + "&c&l is offline");
            return true;
        }
        return false;
    }

    private void sendMessageWithColors(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
