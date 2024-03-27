package me.lotiny.libs.config;

import lombok.Getter;
import me.lotiny.libs.HanaLib;
import me.lotiny.libs.chat.CC;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ConfigFile extends YamlConfiguration {

    private final File file;

    /**
     * Create a `ConfigFile` object with the specified name.
     *
     * @param name The name of the configuration file.
     */
    public ConfigFile(String name) {
        this.file = new File(HanaLib.getInstance().getDataFolder(), name);

        // If the file does not exist in the data folder, attempt to save it from resources.
        if (!this.file.exists()) {
            try {
                HanaLib.getInstance().saveResource(name, false);
            } catch (Exception ex) {
                try {
                    this.file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            // Load the configuration from the file.
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a `ConfigFile` object with the specified name.
     *
     * @param path The path where configuration file.
     * @param name The name of the configuration file.
     */
    public ConfigFile(String path, String name) {
        File folder = new File(HanaLib.getInstance().getDataFolder(), path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        this.file = new File(folder, name);

        // If the file does not exist in the data folder, attempt to save it from resources.
        if (!this.file.exists()) {
            try {
                HanaLib.getInstance().saveResource(path + "/" + name, false);
            } catch (Exception ex) {
                try {
                    this.file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            // Load the configuration from the file.
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the configuration to the file.
     */
    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a value of a specified type from the configuration with a default fallback.
     *
     * @param path         The path to the value in the configuration.
     * @param defaultValue The default value to return if the path is not found or the value is invalid.
     * @param <T>          The type of the value to retrieve.
     * @return The value of the specified type, or the default value if not found or invalid.
     */
    private <T> T getWithDefault(String path, T defaultValue) {
        if (this.isSet(path)) {
            return (T) this.get(path);
        }
        return defaultValue;
    }

    @Override
    public int getInt(String path) {
        return getWithDefault(path, 0);
    }

    @Override
    public double getDouble(String path) {
        return getWithDefault(path, 0.0);
    }

    @Override
    public boolean getBoolean(String path) {
        return getWithDefault(path, false);
    }

    @Override
    public String getString(String path) {
        return getWithDefault(path, "None");
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> list = super.getStringList(path);
        return list.stream().map(CC::translate).collect(Collectors.toList());
    }
}
