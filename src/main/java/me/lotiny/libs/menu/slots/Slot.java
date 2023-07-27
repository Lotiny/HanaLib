package me.lotiny.libs.menu.slots;

import me.lotiny.libs.general.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public abstract class Slot {

    public static boolean hasSlot(List<Slot> slots, int value) {
        return slots.stream()
                .filter(slot -> slot.getSlot() == value || slot.getSlots() != null
                        && Arrays.stream(slot.getSlots()).anyMatch(i -> i == value))
                .findFirst().orElse(null) != null;
    }

    public static void fillGlass(List<Slot> slots, int size) {
        for (int i = 0; i < size; i++) {
            if (!Slot.hasSlot(slots, i)) {
                slots.add(Slot.getGlass(i));
            }
        }
    }

    public static Slot getGlass(int slot) {
        return new Slot() {

            @Override
            public ItemStack getItem(Player player) {
                return new ItemBuilder(getMaterialOr("STAINED_GLASS_PANE", "GRAY_STAINED_GLASS")).setName(" ").setDurability(doesMaterialExists("STAINED_GLASS_PANE") ? 7 : 0).toItemStack();
            }

            @Override
            public int getSlot() {
                return slot;
            }
        };
    }

    private static Material getMaterialOr(String source, String or) {
        try {
            return Material.valueOf(source);
        } catch (Exception e) {
            return getMaterialOr(or, "REDSTONE_BLOCK");
        }
    }

    private static boolean doesMaterialExists(String source) {
        try {
            Material.valueOf(source);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public abstract ItemStack getItem(Player player);

    public abstract int getSlot();

    public void onClick(Player player, int slot, ClickType clickType) {

    }

    public int[] getSlots() {
        return null;
    }

    public boolean hasSlot(int slot) {
        return slot == this.getSlot() || this.getSlots() != null && Arrays.stream(this.getSlots()).anyMatch(i -> i == slot);
    }
}
