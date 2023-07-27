package me.lotiny.libs.general;

import lombok.experimental.UtilityClass;
import me.lotiny.libs.HanaLib;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class Tasks {

    private final JavaPlugin plugin = HanaLib.getInstance();

    public void run(Callable callable) {
        plugin.getServer().getScheduler().runTask(plugin, callable::call);
    }

    public void runAsync(Callable callable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, callable::call);
    }

    public void runLater(Callable callable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, callable::call, delay);
    }

    public void runAsyncLater(Callable callable, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, callable::call, delay);
    }

    public interface Callable {
        void call();
    }
}
