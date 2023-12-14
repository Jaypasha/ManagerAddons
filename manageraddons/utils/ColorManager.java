package jaypasha.owilgrief.manageraddons.utils;

import org.bukkit.ChatColor;

public class ColorManager {
    public ColorManager() {
    }

    public static String Goto(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

