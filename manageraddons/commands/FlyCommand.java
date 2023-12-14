package jaypasha.owilgrief.manageraddons.commands;

import jaypasha.owilgrief.manageraddons.MainClass;
import jaypasha.owilgrief.manageraddons.utils.ColorManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlyCommand implements CommandExecutor {
    public static List<UUID> flyPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("only-players")));
            }
            Player player1 = (Player) sender;
            UUID player = ((Player) sender).getUniqueId();

            if (!sender.hasPermission("manageraddons.fly")) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("not-permission")));
                return true;
            }
            if (flyPlayers.contains(player)) {
                flyPlayers.remove(player);
                player1.setAllowFlight(false);
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-disable")));
            }else {
                player1.setAllowFlight(true);
                flyPlayers.add(player);
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-enable")));
            }
        }
        return true;
    }
}
