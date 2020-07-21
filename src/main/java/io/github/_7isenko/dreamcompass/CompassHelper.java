package io.github._7isenko.dreamcompass;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassHelper {
    @SuppressWarnings("ConstantConditions")
    public static void setTarget(ItemStack compass, Player target) {
        if (target != null) {
            CompassMeta meta = (CompassMeta) compass.getItemMeta();
            meta.setLodestone(target.getLocation());
            meta.setLodestoneTracked(false);
            compass.setItemMeta(meta);
        }
    }
}
