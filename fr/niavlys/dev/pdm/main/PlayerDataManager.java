package fr.niavlys.dev.pdm.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerDataManager {

    private final File file;
    private final FileConfiguration config;

    public PlayerDataManager(String pluginName, String fileName) {
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
        String path = uuid.toString() + "." + key;
        return config.get(path, null);
    }

    public void set(UUID uuid, String key, Object value) {
        String path = uuid.toString() + "." + key;
        config.set(path, value);
        save();
    }

    public void remove(UUID uuid, String key) {
        String path = key == null ? uuid.toString() : uuid.toString() + "." + key;
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
        return config.contains(uuid.toString());
    }

    public FileConfiguration getPlayerData(UUID uuid) {
        return (FileConfiguration) config.getConfigurationSection(uuid.toString());
    }
}
