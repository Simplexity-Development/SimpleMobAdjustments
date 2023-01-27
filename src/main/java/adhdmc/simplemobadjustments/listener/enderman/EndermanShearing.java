package adhdmc.simplemobadjustments.listener.enderman;

import adhdmc.simplemobadjustments.SimpleMobAdjustments;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EndermanShearing implements Listener {

    private final NamespacedKey SHEARED = new NamespacedKey(SimpleMobAdjustments.getPlugin(), "sheared");

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEndermanShear(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.ENDERMAN) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType() != Material.SHEARS) return;
        PersistentDataContainer endermanPDC = event.getRightClicked().getPersistentDataContainer();
        if (endermanPDC.has(SHEARED)) return;
        endermanPDC.set(SHEARED, PersistentDataType.BYTE, (byte) 1);
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        ItemStack item = event.getPlayer().getInventory().getItem(EquipmentSlot.HAND);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setDamage(meta.getDamage() + 1);
        item.setItemMeta(meta);
        ((Mob) event.getRightClicked()).setTarget(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEndermanTeleport(EntityTeleportEvent event) {
        if (event.getEntityType() != EntityType.ENDERMAN) return;
        if (!event.getEntity().getPersistentDataContainer().has(SHEARED)) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEndermanRestoreTeleport(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.ENDERMAN) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType() != Material.ENDER_PEARL) return;
        PersistentDataContainer endermanPDC = event.getRightClicked().getPersistentDataContainer();
        if (!endermanPDC.has(SHEARED)) return;
        endermanPDC.remove(SHEARED);
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).subtract();
    }

}
