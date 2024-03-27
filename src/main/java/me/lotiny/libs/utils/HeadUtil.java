package me.lotiny.libs.utils;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

@UtilityClass
public class HeadUtil {

    @Getter
    public HashMap<String, ItemStack> cachedHeads = new LinkedHashMap<>();;

    public ItemStack getHeadFromValue(String value) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());

        return Bukkit.getUnsafe().modifyItemStack(skull,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
        );
    }

    public ItemStack getHead(String owner) {
        if (cachedHeads.get(owner) != null) {
            return cachedHeads.get(owner);
        }

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(owner);
        meta.setDisplayName("");
        skull.setItemMeta(meta);

        if (!cachedHeads.containsValue(skull)) {
            cachedHeads.put(owner, skull);
        }

        return skull;
    }
}
