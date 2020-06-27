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

import java.util.Objects;

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
                player.getInventory().addItem(new ItemStack(Material.COMPASS));
                player.setCompassTarget(target.getLocation());
                sender.sendMessage(ChatColor.GOLD + "Compass is pointing to " + name);
                Objects.requireNonNull(player.getPlayer()).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lY: &b" + target.getLocation().getBlockY())));
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
