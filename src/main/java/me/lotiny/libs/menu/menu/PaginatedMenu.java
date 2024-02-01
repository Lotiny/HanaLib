package me.lotiny.libs.menu.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lotiny.libs.utils.ItemBuilder;
import me.lotiny.libs.menu.slots.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu extends Menu {

    @Getter
    private int page = 1;

    private static Sound getSoundOr(String source, String or) {
        try {
            return Sound.valueOf(source);
        } catch (Exception e) {
            return getSoundOr(or, "CLICK");
        }
    }

    private static boolean doesSoundExists(String source) {
        try {
            Sound.valueOf(source);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Slot> getSlots(Player player) {
        List<Slot> slots = new ArrayList<>();

        int minSlot = (int) ((double) (page - 1) * ((this.getLines(player) - 2) * 7)) + 1;
        int maxSlot = (int) ((double) (page) * ((this.getLines(player) - 2) * 7));

        if (this.page < this.getPages(player)) {
            slots.add(createNextPageSlot());
        }

        if (this.page > 1) {
            slots.add(createPreviousPageSlot());
        }

        if (this.getBorderSlots(player) != null) {
            this.getBorderSlots(player).forEach(slot -> slots.removeIf(s -> s.hasSlot(slot.getSlot())));
            slots.addAll(this.getBorderSlots(player));
        }

        int index = 1, added = 0, currentSlot = 0;

        for (Slot slot : this.getPaginatedSlots(player)) {
            int current = index++;

            int[] slotValues = {16, 25, 34};

            for (int i = 4; i <= 6; i++) {
                if (this.getLines(player) == i) {
                    if (containsValue(slotValues, currentSlot)) {
                        index += 2;
                        added += 2;
                        current += 2;
                    }
                    break;
                }
            }

            if (current >= minSlot && current <= maxSlot + added) {
                current -= (int) ((double) ((this.getLines(player) - 2) * 7) * (page - 1)) - 9;
                currentSlot = current;

                slots.add(this.getNewSlot(slot, current));
            }
        }

        for (int i = 0; i < this.getLines(player) * 9; i++) {
            if (!Slot.hasSlot(slots, i)) {
                slots.add(Slot.getGlass(i));
            }
        }

        return slots;
    }

    private boolean containsValue(int[] array, int value) {
        for (int item : array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }

    private Slot createNextPageSlot() {
        return new NextPageSlot(this);
    }

    private Slot createPreviousPageSlot() {
        return new PreviousPageSlot(this);
    }

    private Slot getNewSlot(Slot slot, int s) {
        return new Slot() {
            @Override
            public ItemStack getItem(Player player) {
                return slot.getItem(player);
            }

            @Override
            public int getSlot() {
                return s;
            }

            @Override
            public void onClick(Player player, int s, ClickType clickType) {
                slot.onClick(player, s, clickType);
            }
        };
    }

    @Override
    public String getName(Player player) {
        return this.getPagesTitle(player);
    }

    public void changePage(Player player, int page) {
        this.page += page;
        this.getSlots().clear();
        this.update(player);
    }

    public int getPages(Player player) {
        if (this.getPaginatedSlots(player).isEmpty()) {
            return 1;
        }
        return (int) Math.ceil(getPaginatedSlots(player).size() / (double) ((getLines(player) - 2) * 7));
    }

    public abstract String getPagesTitle(Player player);

    public abstract int getLines(Player player);

    public abstract List<Slot> getPaginatedSlots(Player player);

    public abstract List<Slot> getBorderSlots(Player player);

    @RequiredArgsConstructor
    private static class NextPageSlot extends Slot {
        private final PaginatedMenu paginatedMenu;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder item = new ItemBuilder(Material.ARROW);
            item.setName("&aNext page");
            item.addLoreLine("&7Click to go to next page.");
            item.addLoreLine(" ");
            item.addLoreLine("&ePage: &7(&a" + this.paginatedMenu.getPage() + "&7/&a" + this.paginatedMenu.getPages(player) + "&7)");

            return item.toItemStack();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            player.playSound(player.getLocation(), getSoundOr("CLICK", "UI_BUTTON_CLICK"), 1f, 1f);
            this.paginatedMenu.changePage(player, 1);
        }

        @Override
        public int getSlot() {
            return 8;
        }

        @Override
        public int[] getSlots() {
            return null;
        }
    }

    @RequiredArgsConstructor
    private static class PreviousPageSlot extends Slot {

        private final PaginatedMenu paginatedMenu;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder item = new ItemBuilder(Material.ARROW);
            item.setName("&cPrevious Page");
            item.addLoreLine("&7Click to go back to previous page.");
            item.addLoreLine(" ");
            item.addLoreLine("&ePage: &7(&a" + this.paginatedMenu.getPage() + "&7/&a" + this.paginatedMenu.getPages(player) + "&7)");

            return item.toItemStack();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            player.playSound(player.getLocation(), getSoundOr("CLICK", "UI_BUTTON_CLICK"), 1f, 1f);
            this.paginatedMenu.changePage(player, -1);
        }

        @Override
        public int getSlot() {
            return 0;
        }

        @Override
        public int[] getSlots() {
            return null;
        }
    }
}
