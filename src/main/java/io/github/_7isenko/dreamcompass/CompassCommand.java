package io.github._7isenko.dreamcompass;

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
    @SuppressWarnings("ConstantConditions")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is available only for players");
            return false;
        }
        if (args.length != 1) return false;

        Player player = (Player) sender;
        String name = args[0];
        Player target = Bukkit.getPlayer(name);

        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta meta = (CompassMeta) compass.getItemMeta();
        meta.setDisplayName(name);
        compass.setItemMeta(meta);
        if (target != null) {
            DreamCompass.helper.setTarget(player, compass, target);
            sender.sendMessage(ChatColor.GOLD + "New compass is pointing to " + name);
        } else
            sender.sendMessage(ChatColor.GOLD + "New compass will point to " + name + " once this player is online");

        player.getInventory().addItem(compass);
        return true;
    }

}
