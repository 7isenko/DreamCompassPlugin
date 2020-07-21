package io.github._7isenko.dreamcompass;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@SuppressWarnings("unused")
public final class DreamCompass extends JavaPlugin {

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
