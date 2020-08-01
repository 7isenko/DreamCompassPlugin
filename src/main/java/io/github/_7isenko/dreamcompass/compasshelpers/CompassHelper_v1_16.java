package io.github._7isenko.dreamcompass.compasshelpers;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassHelper_v1_16 implements CompassHelper {
    @SuppressWarnings("ConstantConditions")
    public void setTarget(Player player, ItemStack compass, Player target) {
        if (target != null) {
            CompassMeta meta = (CompassMeta) compass.getItemMeta();
            meta.setLodestone(target.getLocation());
            meta.setLodestoneTracked(false);
            compass.setItemMeta(meta);
        }
    }
}
