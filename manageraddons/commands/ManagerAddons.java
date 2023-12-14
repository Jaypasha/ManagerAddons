package jaypasha.owilgrief.manageraddons.commands;

import jaypasha.owilgrief.manageraddons.MainClass;
import jaypasha.owilgrief.manageraddons.utils.ColorManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerAddons implements CommandExecutor, TabExecutor {

    public static List<String> stringManager = MainClass.getInstance().getConfig().getStringList("manager-admin-args");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("manageraddons")) {
            if (args.length == 0) {
                for (String string : stringManager) {
                    sender.sendMessage(ColorManager.Goto(string));
                }
                return true;
            }

            String action = args[0];

            if (action.equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("manageraddons-admin")) {
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("not-permission")));
                    return true;
                }
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("reload-confirm")));
                MainClass.getInstance().reloadConfig();
                return true;
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("reload");
        }
        return new ArrayList<>();
    }
}