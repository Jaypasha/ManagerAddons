package jaypasha.owilgrief.manageraddons.commands;

import jaypasha.owilgrief.manageraddons.MainClass;
import jaypasha.owilgrief.manageraddons.utils.ColorManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlyCommandAdmin implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("manageraddons-fly")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("only-players")));
                return true;
                }
            if (args.length == 0) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-admin-args")));
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-admin-args")));
                return true;
            }
            String targetName = args[0];
            String OnOff = args[1];
            Player target = Bukkit.getPlayer(args[0]);
            if (!sender.hasPermission("manageraddons.admin")) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("not-permission")));
                return true;
            }
            if (OnOff.equalsIgnoreCase("disable")) {
                if (!FlyCommand.flyPlayers.contains(target.getUniqueId())) {
                    sender.sendMessage(ChatColor.GOLD + "У данного игрока нету режима " + ChatColor.RED + "полёта");
                    return true;
                }
                target.setAllowFlight(false);
                target.setFlying(false);
                FlyCommand.flyPlayers.remove(target.getUniqueId());
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-disable-admin").replace("%targetName%", targetName)));
                target.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-disable")));
            }
            if (OnOff.equalsIgnoreCase("enable")) {
                if (FlyCommand.flyPlayers.contains(target.getUniqueId())) {
                    sender.sendMessage(ChatColor.GOLD + "Игрок уже находится в режиме " + ChatColor.RED + "полёта");
                    return true;
                }
                target.setAllowFlight(true);
                target.setFlying(true);
                FlyCommand.flyPlayers.add(target.getUniqueId());
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-enable-admin").replace("%targetName%", targetName)));
                target.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("fly-enable")));
            }
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return null;
        }
        if (strings.length == 2) {
            return Arrays.asList("enable", "disable");
        }
        return new ArrayList<>();
    }
}
