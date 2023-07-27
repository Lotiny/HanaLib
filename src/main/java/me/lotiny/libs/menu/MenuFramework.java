package me.lotiny.libs.menu;

import lombok.Getter;
import me.lotiny.libs.HanaLib;
import me.lotiny.libs.menu.menu.Menu;
import me.lotiny.libs.menu.slots.Slot;
import me.lotiny.libs.utils.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuFramework implements Listener {

    @Getter
    public static MenuFramework menuFramework;

    @Getter
    public Map<UUID, Menu> openedMenus = new HashMap<>();

    public MenuFramework() {
        menuFramework = this;

        run();
        Bukkit.getPluginManager().registerEvents(this, HanaLib.getInstance());
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                BukkitUtil.getOnlinePlayers().forEach(player -> {
                    Menu menu = getOpenedMenu(player);
                    if (menu != null && menu.isUpdateInTask()) {
                        menu.update(player);
                    }
                });
            }
        }.runTaskTimerAsynchronously(HanaLib.getInstance(), 20L, 20L);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = getOpenedMenu(player);

        if (menu == null) {
            return;
        }

        event.setCancelled(true);

        if (event.getSlot() != event.getRawSlot()) {
            return;
        }

        Slot slot = menu.getSlot(event.getSlot());
        if (slot == null) {
            return;
        }

        slot.onClick(player, event.getSlot(), event.getClick());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu menu = getOpenedMenu(player);

        if (menu == null) {
            return;
        }

        menu.onClose(player);
        openedMenus.remove(player.getUniqueId());
    }

    public Menu getOpenedMenu(Player player) {
        UUID playerId = player.getUniqueId();
        return openedMenus.get(playerId);
    }
}
