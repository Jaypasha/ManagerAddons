package jaypasha.owilgrief.manageraddons.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    public void Config() {
    }

    public static FileConfiguration getFile(String fileName) {
        File file = new File(Tools.getInstance().getDataFolder(), fileName);
        if (!file.exists()) {
            Tools.getInstance().saveResource(fileName, false);
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public static void save(FileConfiguration config, String fileName) {
        try {
            config.save(new File(Tools.getInstance().getDataFolder(), fileName));
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}
