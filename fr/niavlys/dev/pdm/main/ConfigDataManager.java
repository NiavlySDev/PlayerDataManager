package fr.niavlys.dev.pdm.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigDataManager {

    private final File file;
    private final FileConfiguration config;

    public ConfigDataManager(String pluginName, String fileName) {
        File pluginFolder = new File("plugins/" + pluginName);
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        this.file = new File(pluginFolder, fileName);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public Object get(UUID uuid, String key) {
        return get(uuid.toString(), key);
    }

    public Object get(String objet, String key) {
        String path = objet + "." + key;
        return config.get(path, null);
    }

    public void set(UUID uuid, String key, Object value) {
        set(uuid.toString(), key, value);
    }

    public void set(String objet, String key, Object value) {
        String path = objet + "." + key;
        config.set(path, value);
        save();
    }

    public void remove(UUID uuid, String key) {
        remove(uuid.toString(), key);
    }

    public void remove(String objet, String key) {
        String path = key == null ? objet : objet + "." + key;
        config.set(path, null);
        save();
    }

    private void save() {
        try {
            config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        return exists(uuid.toString());
    }
    public boolean exists(String objet) {
        return config.contains(objet);
    }

    public FileConfiguration getPlayerData(UUID uuid) {
        return (FileConfiguration) config.getConfigurationSection(uuid.toString());
    }
}
