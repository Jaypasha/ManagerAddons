package jaypasha.owilgrief.manageraddons;

import jaypasha.owilgrief.manageraddons.commands.*;
import jaypasha.owilgrief.manageraddons.events.Events;
import jaypasha.owilgrief.manageraddons.utils.Tools;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MainClass extends JavaPlugin {
    private static MainClass instance;
    @Override
    public void onEnable() {
        instance = this;
        Tools.setInstance(this);
        getServer().getPluginCommand("fly").setExecutor(new FlyCommand());
        getServer().getPluginCommand("manageraddons-fly").setExecutor(new FlyCommandAdmin());
        getServer().getPluginCommand("manageraddons-gab").setExecutor(new GabCommandAdmin());
        getServer().getPluginCommand("manageraddons-trolling").setExecutor(new TrollingPlayersDamageAdmin());
        getServer().getPluginCommand("gab").setExecutor(new GabCommand());
        getServer().getPluginCommand("manageraddons").setExecutor(new jaypasha.owilgrief.manageraddons.commands.ManagerAddons());
        getServer().getPluginManager().registerEvents(new Events(), this);
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults();
            this.saveDefaultConfig();
        }
    }


    @Override
    public void onDisable() {
    }
    public static MainClass getInstance() {
        return instance;
    }
}
