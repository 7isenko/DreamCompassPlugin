package io.github._7isenko.dreamcompass;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

public final class DreamCompass extends JavaPlugin implements Listener {
    public static HashMap<Player, String> hunters = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new CompassInteractListener(), this);
        Objects.requireNonNull(this.getCommand("compass")).setExecutor(new CompassCommand());
    }

    @Override
    public void onDisable() {
        // nothing
    }
}
