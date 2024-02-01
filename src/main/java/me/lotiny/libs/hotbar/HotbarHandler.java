package me.lotiny.libs.hotbar;

import lombok.Getter;
import me.lotiny.libs.HanaLib;
import me.lotiny.libs.hotbar.item.HotbarItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HotbarHandler {

    private final List<HotbarItem> items = new ArrayList<>();

    public HotbarHandler() {
        Bukkit.getPluginManager().registerEvents(new HotbarListener(), HanaLib.getInstance());
    }

    public HotbarItem getItemByItem(ItemStack itemStack) {
        return items.stream().filter(item -> item.getItem().isSimilar(itemStack)).findFirst().orElse(null);
    }
}
