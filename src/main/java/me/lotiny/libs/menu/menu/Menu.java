package me.lotiny.libs.menu.menu;

import lombok.Getter;
import lombok.Setter;
import me.lotiny.libs.chat.CC;
import me.lotiny.libs.general.Tasks;
import me.lotiny.libs.menu.MenuFramework;
import me.lotiny.libs.menu.slots.Slot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Menu {

    private final MenuFramework menuFramework = MenuFramework.getMenuFramework();

    @Getter
    private List<Slot> slots = new ArrayList<>();
    @Setter
    @Getter
    private boolean updateInTask = false;

    public abstract String getName(Player player);

    public abstract List<Slot> getSlots(Player player);

    public void open(Player player) {
        Menu previous = menuFramework.getOpenedMenu(player);
        if (previous != null) {
            previous.onClose(player);
            menuFramework.getOpenedMenus().remove(player.getUniqueId());
        }

        this.slots = this.getSlots(player);
        String title = this.getName(player);
        title = CC.translate(title.substring(0, Math.min(title.length(), 32)));

        Inventory inventory = Bukkit.createInventory(player, this.getInventorySize(this.slots), title);

        getContents(player, inventory);

        Tasks.run(() -> {
            player.closeInventory();
            menuFramework.getOpenedMenus().put(player.getUniqueId(), this);
            player.openInventory(inventory);

            onOpen(player);
        });
    }

    public void update(Player player) {
        this.slots = this.getSlots(player);
        String title = this.getName(player);
        title = CC.translate(title);

        if (title.length() > 32) {
            title = title.substring(0, 32);
        }

        Inventory inventory;
        Menu currentlyOpenedMenu = menuFramework.getOpenedMenu(player);
        Inventory current = player.getOpenInventory().getTopInventory();

        boolean passed = currentlyOpenedMenu != null && CC.translate(currentlyOpenedMenu.getName(player))
                .equals(player.getOpenInventory().getTitle()) && current.getSize() == this.getInventorySize(this.slots);

        if (passed) {
            inventory = current;
        } else {
            inventory = Bukkit.createInventory(player, this.getInventorySize(this.slots), title);
        }

        getContents(player, inventory);

        menuFramework.getOpenedMenus().put(player.getUniqueId(), this);

        if (passed) {
            player.updateInventory();
        } else {
            Tasks.run(() -> player.openInventory(inventory));
        }
    }

    private void getContents(Player player, Inventory temporaryInventory) {
        this.slots.forEach(slot -> {
            temporaryInventory.setItem(slot.getSlot(), slot.getItem(player));

            if (slot.getSlots() != null) {
                Arrays.stream(slot.getSlots()).forEach(extra -> {
                    temporaryInventory.setItem(extra, slot.getItem(player));
                });
            }
        });
    }

    private int getInventorySize(List<Slot> slots) {
        int highest = 0;

        for (Slot slot : slots) {
            highest = Math.max(highest, slot.getSlot());

            if (slot.getSlots() != null) {
                for (int slotValue : slot.getSlots()) {
                    highest = Math.max(highest, slotValue);
                }
            }
        }

        return (int) Math.ceil((highest + 1) / 9D) * 9;
    }

    public Slot getSlot(int value) {
        return this.slots.stream()
                .filter(slot -> slot.getSlot() == value || slot.getSlots() != null
                        && Arrays.stream(slot.getSlots()).anyMatch(i -> i == value))
                .findFirst().orElse(null);
    }

    public void onOpen(Player player) {

    }

    public void onClose(Player player) {

    }
}
