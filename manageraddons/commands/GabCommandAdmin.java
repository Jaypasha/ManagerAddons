package jaypasha.owilgrief.manageraddons.commands;

import jaypasha.owilgrief.manageraddons.MainClass;
import jaypasha.owilgrief.manageraddons.utils.ColorManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GabCommandAdmin implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("manageraddons-gab")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("only-players")));
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("gab-admin-args")));
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("gab-admin-args")));
                return true;
            }
            String OnOff = args[1];
            Player target = Bukkit.getPlayer(args[0]);
            String targetName = args[0];
            if (!sender.hasPermission("manageraddons.admin")) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("not-permission")));
                return true;
            }
            if (OnOff.equalsIgnoreCase("disable")) {
                if (!GabCommand.gabPlayers.contains(target.getUniqueId())) {
                    sender.sendMessage(ChatColor.GOLD + "У данного игрока нету режима " + ChatColor.RED + "бога");
                    return true;
                }
                GabCommand.gabPlayers.remove(target.getUniqueId());
                sender.sendMessage(ChatColor.GOLD + "режим бога" + ChatColor.RED + " выключен" + ChatColor.YELLOW + " игроку " + targetName);
                target.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("gab-disable")));
            }
                if (OnOff.equalsIgnoreCase("enable")) {
                    if (GabCommand.gabPlayers.contains(target.getUniqueId())) {
                        sender.sendMessage(ChatColor.GOLD + "Игрок уже находится в режиме " + ChatColor.RED + "бога");
                        return true;
                    }
                    GabCommand.gabPlayers.add(target.getUniqueId());
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("gab-enable-admin").replace("%targetName%", targetName)));
                    target.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("gab-enable")));
            }
        }
        return true;
    }

        @Override
        public @Nullable List<String> onTabComplete (@NotNull CommandSender commandSender, @NotNull Command
        command, @NotNull String s, @NotNull String[]strings){
            if (strings.length == 1) {
                return null;
            }
            if (strings.length == 2) {
                return Arrays.asList("enable", "disable");
            }
            return new ArrayList<>();
        }
}
