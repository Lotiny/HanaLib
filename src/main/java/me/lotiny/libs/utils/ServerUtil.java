package me.lotiny.libs.utils;

import lombok.experimental.UtilityClass;
import me.lotiny.libs.HanaLib;
import me.lotiny.libs.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ServerUtil {

    /**
     * Log a message to the server console with the plugin name prefix.
     *
     * @param message The message to log.
     */
    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + HanaLib.getInstance().getDescription().getName() + "] " + CC.translate(message));
    }

    /**
     * Send a message to all online players.
     *
     * @param message The message to send.
     */
    public void sendMessage(String message) {
        Bukkit.broadcastMessage(CC.translate(message));
    }

    /**
     * Retrieve a list of all online players.
     *
     * @return A list of online players.
     */
    public List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
    }
}
