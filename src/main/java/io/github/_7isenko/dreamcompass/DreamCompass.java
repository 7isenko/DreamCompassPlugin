package io.github._7isenko.dreamcompass;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public final class DreamCompass extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public static java.util.Random rand = new java.util.Random();

    public static int epsilon;

    public static Location loc0;
    public static Location loc1;
    public static Location loc2;

    public static int minimumDist;

    public static int maximumX;
    public static int maximumZ;

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        reloadConfig();
        saveConfig();
    }

    public static boolean hasAdvancement(Player player, String achname) {
        Advancement ach = null;
        for (Iterator<Advancement> iter = Bukkit.getServer().advancementIterator(); iter.hasNext(); ) {
            Advancement adv = iter.next();
            if (adv.getKey().getKey().equalsIgnoreCase(achname)) {
                ach = adv;
                break;
            }
        }
        AdvancementProgress prog = player.getAdvancementProgress(ach);
        if (prog.isDone()) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand().getType() == Material.COMPASS) {
            if (Map.containsKey(e.getPlayer())) {
                Player target = Bukkit.getPlayer(Map.get(e.getPlayer()));
                if (target != null) {
                    boolean mode = getConfig().getBoolean("DefaultMode");
                    if (mode) {
                        e.getPlayer().setCompassTarget(target.getLocation());
                        e.getPlayer().sendMessage(ChatColor.GOLD + "Compass pointing to " + Map.get(e.getPlayer()));
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lY: &b" + target.getLocation().getBlockY())));
                        wtfMethod(target);
                    }
                    if (!mode) {
                        e.getPlayer().setCompassTarget(target.getLocation());
                        e.getPlayer().sendMessage(ChatColor.GOLD + "Compass pointing to " + Map.get(e.getPlayer()));
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lX: &b" + target.getLocation().getBlockX() + "   &3&lY: &b" + target.getLocation().getBlockY() + "   &3&lZ: &b" + target.getLocation().getBlockZ() + "   &c&lWorld: &e" + target.getWorld().getName())));
                        wtfMethod(target);
                    }
                } else {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPlayer &4&l" + Map.get(e.getPlayer()) + "&c&l is offline"));
                }

            }
        }
    }

    private void wtfMethod(Player target) {
        int EnderChance = getConfig().getInt("EnderChance");
        if (EnderChance > 0 && EnderChance <= 100) {
            if (hasAdvancement(target, "nether/obtain_blaze_rod")) {
                if (target.getWorld().getName().equals("world")) {
                    double EnderChanceDouble = ((double) EnderChance / (double) 100);
                    double r = rand.nextDouble();
                    if (r >= 1 - EnderChanceDouble) {

                        int minimumDist = getConfig().getInt("MinimumDistance");
                        int maximumX = getConfig().getInt("MaximumDistance");
                        int maximumZ = getConfig().getInt("MaximumDistance");

                        int randomX = ThreadLocalRandom.current().nextInt((target.getLocation().getBlockX() - maximumX), (target.getLocation().getBlockX() + maximumX + 1));
                        int randomZ = ThreadLocalRandom.current().nextInt((target.getLocation().getBlockZ() - maximumZ), (target.getLocation().getBlockZ() + maximumZ + 1));

                        Biome biome = target.getWorld().getBiome(target.getLocation().getBlockX(), target.getLocation().getBlockZ());

                        if ((biome == Biome.MUTATED_SAVANNA_ROCK || biome == Biome.EXTREME_HILLS || biome == Biome.EXTREME_HILLS_WITH_TREES || biome == Biome.MUTATED_EXTREME_HILLS || biome == Biome.SMALLER_EXTREME_HILLS || biome == Biome.MUTATED_MESA || biome == Biome.MUTATED_ICE_FLATS || biome == Biome.ICE_MOUNTAINS || biome == Biome.MUTATED_EXTREME_HILLS_WITH_TREES) || (target.getLocation().getBlockY() > 120)) {
                            epsilon = (target.getLocation().getBlockY() - 70);
                        } else {
                            epsilon = (target.getLocation().getBlockY() - 15);
                        }

                        Location loc0 = new Location(target.getWorld(), randomX, epsilon, randomZ);
                        Location loc1 = new Location(target.getWorld(), randomX, epsilon + 1, randomZ);
                        Location loc2 = new Location(target.getWorld(), randomX, epsilon + 2, randomZ);

                        Location targetloc = target.getLocation();
                        while (loc0.distance(targetloc) < minimumDist) {
                            randomX = ThreadLocalRandom.current().nextInt((target.getLocation().getBlockX() - maximumX), (target.getLocation().getBlockX() + maximumX + 1));
                            randomZ = ThreadLocalRandom.current().nextInt((target.getLocation().getBlockZ() - maximumZ), (target.getLocation().getBlockZ() + maximumZ + 1));
                            loc0 = new Location(target.getWorld(), randomX, epsilon, randomZ);
                            if (loc0.distance(targetloc) < minimumDist) {
                                break;
                            }
                        }
                        for (int i = 2; i < 200; ++i) {
                            ++epsilon;
                            loc0 = new Location(target.getWorld(), randomX, epsilon, randomZ);
                            loc1 = new Location(target.getWorld(), randomX, epsilon + 1, randomZ);
                            loc2 = new Location(target.getWorld(), randomX, epsilon + 2, randomZ);
                            if (loc0.getBlock().getType() == Material.AIR && loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
                                target.getWorld().spawnEntity(loc0, EntityType.ENDERMAN);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    HashMap<Player, String> Map = new HashMap<Player, String>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("compass")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUse /compass <player> or /compass reload"));
                    return true;
                }

                if (args.length == 1) {
                    String name = args[0];
                    Player target = Bukkit.getPlayer(name);
                    if (args[0].equalsIgnoreCase("help")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3 Use &c&l/compass <name> &3to get compass, use &c&l/compass reload &3and use &c&l/help &3to get this help message"));
                    } else {
                        if (args[0].equalsIgnoreCase("reload")) {
                            reloadConfig();
                            getConfig();
                            saveConfig();
                            player.sendMessage(ChatColor.DARK_AQUA + "Config reloaded");
                        } else {
                            if (target == null) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPlayer &4&l" + name + "&c&l is offline"));
                                return true;
                            } else {
                                Map.put(player, name);
                                player.setItemInHand(new ItemStack(Material.COMPASS));
                                player.setCompassTarget(target.getLocation());
                                sender.sendMessage(ChatColor.GOLD + "Compass pointing to " + name);
                                boolean mode = getConfig().getBoolean("DefaultMode");
                                if (mode) {
                                    player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lY: &b" + target.getLocation().getBlockY())));
                                } else {
                                    player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&3&lX: &b" + target.getLocation().getBlockX() + "   &3&lY: &b" + target.getLocation().getBlockY() + "   &3&lZ: &b" + target.getLocation().getBlockZ() + "   &c&lWorld: &e" + target.getWorld().getName())));
                                }

                                return true;
                            }
                        }
                    }
                }
                return true;

            }
            return false;
        }
        return false;
    }
}
