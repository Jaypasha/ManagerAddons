package jaypasha.owilgrief.manageraddons.commands;

import jaypasha.owilgrief.manageraddons.MainClass;
import jaypasha.owilgrief.manageraddons.utils.ColorManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TrollingPlayersDamageAdmin implements CommandExecutor, TabExecutor {
    public static List<UUID> trollingEffectsPlayers = new ArrayList<>();
    public static List<UUID> trollingDamagePlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("manageraddons-trolling")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("only-players")));
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("trolling-admin-args")));
                return true;
            }
            int dura = 999999;
            int ampli = 3;
            Player target = Bukkit.getPlayer(args[0]);
            String targetName = args[0];
            String action = args[1];
            if (target == null) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("player-not-found")));
            }
            if (!action.equalsIgnoreCase("damage") && !action.equalsIgnoreCase("effects") && !action.equalsIgnoreCase("another")) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("trolling-admin-args")));
            }
            if (!sender.hasPermission("manageraddons-admin")) {
                sender.sendMessage("not-permission");
                return true;
            }
            if (action.equalsIgnoreCase("damage")) {
                if (trollingDamagePlayers.contains(target.getUniqueId())) {
                    trollingDamagePlayers.remove(target.getUniqueId());
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("trolling-disable-damage").replace("%suspect%", targetName)));
                } else {
                    trollingDamagePlayers.add(target.getUniqueId());
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("trolling-enable-damage").replace("%suspect%", targetName)));
                }
            }
            if (!sender.hasPermission("manageraddons-admin")) {
                sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("not-permission")));
                return true;
            }
            if (action.equalsIgnoreCase("effects")) {
                if (trollingEffectsPlayers.contains(target.getUniqueId())) {
                    trollingEffectsPlayers.remove(target.getUniqueId());
                    target.removePotionEffect(PotionEffectType.BLINDNESS);
                    target.removePotionEffect(PotionEffectType.SLOW);
                    target.removePotionEffect(PotionEffectType.HUNGER);
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("trolling-disable-effects").replace("%suspect%", targetName)));
                } else {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, dura, ampli));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, dura, ampli));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, dura, ampli));
                    trollingEffectsPlayers.add(target.getUniqueId());
                    sender.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("trolling-enable-effects").replace("%suspect%", targetName)));
                }
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
            return Arrays.asList("damage", "effects");
        }
        return new ArrayList<>();
    }
}
