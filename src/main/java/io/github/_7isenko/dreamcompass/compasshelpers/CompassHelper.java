package io.github._7isenko.dreamcompass.compasshelpers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface CompassHelper {
    void setTarget(Player player, ItemStack compass, Player target);
}
