package me.lotiny.libs.hotbar;

import me.lotiny.libs.HanaLib;
import me.lotiny.libs.hotbar.item.HotbarItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HotbarListener implements Listener {

    private final HanaLib hanaLib = HanaLib.getHanaLib();

    @EventHandler
    public void handlePlayerDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (hanaLib.getHotbarHandler().getItemByItem(item) == null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerMoveItem(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (hanaLib.getHotbarHandler().getItemByItem(item) == null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        ItemStack item = player.getItemInHand();
        if (item == null || !event.hasItem()) {
            return;
        }

        HotbarItem hotbarItem = hanaLib.getHotbarHandler().getItemByItem(item);
        if (hotbarItem == null) {
            return;
        }

        if (!hotbarItem.getClickCommand().isEmpty()) {
            player.performCommand(hotbarItem.getClickCommand());
        }
    }
}
