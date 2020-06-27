package io.github._7isenko.dreamcompass;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

public final class DreamCompass extends JavaPlugin implements Listener {
    HashMap<Player, String> Map = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("compass")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        reloadConfig();
        saveConfig();
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.COMPASS || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.COMPASS) {
            if (Map.containsKey(e.getPlayer())) {
                Player target = Bukkit.getPlayer(Map.get(e.getPlayer()));
                if (target != null) {
                    e.getPlayer().setCompassTarget(target.getLocation());
                    e.getPlayer().sendMessage(ChatColor.GOLD + "Compass is pointing to " + Map.get(e.getPlayer()));
                    e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lY: &b" + target.getLocation().getBlockY())));
                } else {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPlayer &4&l" + Map.get(e.getPlayer()) + "&c&l is offline"));
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        if (args.length == 0) {
            sendMessageWithColors(sender, "&c&lUse /compass <player>");
            return true;
        }

        if (args.length == 1) {
            String name = args[0];
            Player target = Bukkit.getPlayer(name);
            if (target == null) {
                sendMessageWithColors(sender, "&c&lPlayer &4&l" + name + "&c&l is offline");
            } else {
                Map.put(player, name);
                player.getInventory().addItem(new ItemStack(Material.COMPASS));
                player.setCompassTarget(target.getLocation());
                sender.sendMessage(ChatColor.GOLD + "Compass is pointing to " + name);
                Objects.requireNonNull(player.getPlayer()).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lY: &b" + target.getLocation().getBlockY())));
            }
            return true;
        }
        return false;
    }

    private void sendMessageWithColors(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
