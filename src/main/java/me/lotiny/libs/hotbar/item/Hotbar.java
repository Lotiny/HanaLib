package me.lotiny.libs.hotbar.item;

import me.lotiny.libs.HanaLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public abstract class Hotbar implements Listener {

    public Hotbar() {
        if (hasListeners()) {
            Bukkit.getPluginManager().registerEvents(this, HanaLib.getInstance());
        }
    }

    public abstract boolean hasListeners();

    public abstract List<HotbarItem> getItems();

    public void apply(Player player, boolean clearItems, boolean clearArmors) {
        PlayerInventory inventory = player.getInventory();

        if (clearArmors) {
            inventory.setArmorContents(null);
        }
        if (clearItems) {
            inventory.clear();
        }

        getItems().forEach(item -> {
            if (!item.getPermission().isEmpty()) {
                if (player.hasPermission(item.getPermission())) {
                    inventory.setItem(item.getSlot(), item.getItem());
                }
            } else {
                inventory.setItem(item.getSlot(), item.getItem());
            }
        });
    }
}
