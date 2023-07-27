package me.lotiny.libs;

import org.bukkit.plugin.java.JavaPlugin;

public class HanaLib {

    public static volatile JavaPlugin instance;

    public static JavaPlugin getInstance() {
        if (instance == null) {
            throw new IllegalStateException("HanaLib hasn't been initialized yet.");
        }
        return instance;
    }

    public static void init(JavaPlugin plugin) {
        if (instance != null) {
            throw new IllegalStateException("HanaLib has already been initialized.");
        }
        instance = plugin;
    }
}
