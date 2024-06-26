package me.lotiny.libs.hotbar.item;

import lombok.Getter;
import me.lotiny.libs.HanaLib;
import org.bukkit.inventory.ItemStack;

@Getter
public class HotbarItem {

    private final int slot;
    private final ItemStack item;
    private final String permission;
    private final String clickCommand;
    private final String action;

    public HotbarItem(int slot, ItemStack item, String permission, String clickCommand, String action) {
        this.slot = slot;
        this.item = item;
        this.permission = permission;
        this.clickCommand = clickCommand;
        this.action = action;

        HanaLib.getHanaLib().getHotbarHandler().getItems().add(this);
    }
}
