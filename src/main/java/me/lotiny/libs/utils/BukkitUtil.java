package me.lotiny.libs.utils;

import lombok.experimental.UtilityClass;
import me.lotiny.libs.chat.CC;
import me.lotiny.libs.HanaLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BukkitUtil {

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + HanaLib.getInstance().getDescription().getName() + "] " + CC.translate(message));
    }

    public void sendMessage(String message) {
        Bukkit.broadcastMessage(CC.translate(message));
    }

    public List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
    }
}
