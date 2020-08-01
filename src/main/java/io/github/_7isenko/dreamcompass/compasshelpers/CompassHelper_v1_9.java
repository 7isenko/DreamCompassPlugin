package io.github._7isenko.dreamcompass.compasshelpers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CompassHelper_v1_9 implements CompassHelper {
    @Override
    public void setTarget(Player player, ItemStack compass, Player target) {
        if (target != null) {
            player.setCompassTarget(target.getLocation());
        } else player.setCompassTarget(player.getWorld().getSpawnLocation());
    }
}
