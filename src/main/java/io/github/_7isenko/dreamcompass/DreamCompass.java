package io.github._7isenko.dreamcompass;

import io.github._7isenko.dreamcompass.compasshelpers.CompassHelper;
import io.github._7isenko.dreamcompass.compasshelpers.CompassHelper_v1_16;
import io.github._7isenko.dreamcompass.compasshelpers.CompassHelper_v1_9;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Scanner;

@SuppressWarnings("unused")
public final class DreamCompass extends JavaPlugin {
    public static CompassHelper helper;
    private float version;

    @Override
    public void onEnable() {
        version = new Scanner(Bukkit.getServer().getBukkitVersion()).nextFloat();

        if (version >= 1.16)
            helper = new CompassHelper_v1_16();
        else helper = new CompassHelper_v1_9();

        Bukkit.getPluginManager().registerEvents(new CompassInteractListener(), this);
        Objects.requireNonNull(this.getCommand("compass")).setExecutor(new CompassCommand());
    }

    @Override
    public void onDisable() {
        // nothing
    }
}
