package jaypasha.owilgrief.manageraddons.utils;

import org.bukkit.plugin.java.JavaPlugin;

public class Tools {
    private static JavaPlugin instance;

    public Tools() {
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static void setInstance(JavaPlugin instance) {
        Tools.instance = instance;
    }

}
