package me.lotiny.libs;

import lombok.Getter;
import me.lotiny.libs.command.CommandHandler;
import me.lotiny.libs.hotbar.HotbarHandler;
import me.lotiny.libs.menu.MenuHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HanaLib {

    @Getter
    private static HanaLib hanaLib;
    @Getter
    private static JavaPlugin instance;

    private final CommandHandler commandHandler;
    private final MenuHandler menuHandler;
    private final HotbarHandler hotbarHandler;

    /**
     * Constructor for the HanaLib class.
     * <p>
     * Initializes the HanaLib instance with the provided JavaPlugin.
     * This constructor ensures that only one instance of HanaLib can be created.
     *
     * @param plugin The JavaPlugin instance that HanaLib is associated with.
     * @throws IllegalStateException If HanaLib has already been initialized, an exception is thrown.
     */
    public HanaLib(JavaPlugin plugin) {
        if (instance != null) {
            throw new IllegalStateException("HanaLib has already been initialized.");
        }

        hanaLib = this;
        instance = plugin;

        commandHandler = new CommandHandler();
        menuHandler = new MenuHandler();
        hotbarHandler = new HotbarHandler();
    }
}
