package me.lotiny.libs.config;

import lombok.Getter;
import me.lotiny.libs.chat.CC;
import me.lotiny.libs.HanaLib;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFile extends YamlConfiguration {

    @Getter
    private final File file;

    public ConfigFile(String name) {
        this.file = new File(HanaLib.getInstance().getDataFolder(), name);

        if (!this.file.exists()) {
            HanaLib.getInstance().saveResource(name, false);
        }

        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getInt(String path) {
        return super.getInt(path, 0);
    }

    @Override
    public double getDouble(String path) {
        return super.getDouble(path, 0.0);
    }

    @Override
    public boolean getBoolean(String path) {
        return super.getBoolean(path, false);
    }

    public String getString(String path, boolean ignored) {
        return super.getString(path, null);
    }

    @Override
    public String getString(String path) {
        return super.getString(path, "&eString at path &7'&6" + path + "&7'&e not found.").replace("{0}", "\n");
    }

    @Override
    public List<String> getStringList(String path) {
        return super.getStringList(path).stream().map(CC::translate).collect(Collectors.toList());
    }

    public List<String> getStringList(String path, boolean ignored) {
        if (!super.contains(path)) return null;
        return super.getStringList(path).stream().map(CC::translate).collect(Collectors.toList());
    }

    public List<String> getStringList(String path, List<String> def) {
        if (!super.contains(path)) return def;
        return super.getStringList(path).stream().map(CC::translate).collect(Collectors.toList());
    }
}
