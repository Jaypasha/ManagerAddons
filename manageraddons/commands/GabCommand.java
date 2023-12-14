package jaypasha.owilgrief.manageraddons.commands;

import jaypasha.owilgrief.manageraddons.MainClass;
import jaypasha.owilgrief.manageraddons.utils.ColorManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GabCommand implements CommandExecutor {
    public static List<UUID> gabPlayers = new ArrayList<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] strings) {
        if (cmd.getName().equalsIgnoreCase("gab")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("only-players")));
            }
            UUID player = ((Player) sender).getUniqueId();
            if (!sender.hasPermission("manageraddons.gab")) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("not-permission")));
                return true;
            }
                if (gabPlayers.contains(player)) {
                    gabPlayers.remove(player);
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("gab-disable")));
                }else {
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("gab-enable")));
                    gabPlayers.add(player);
                }
            }
        return true;
    }
}
