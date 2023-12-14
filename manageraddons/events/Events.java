package jaypasha.owilgrief.manageraddons.events;

import jaypasha.owilgrief.manageraddons.MainClass;
import jaypasha.owilgrief.manageraddons.commands.FlyCommand;
import jaypasha.owilgrief.manageraddons.commands.GabCommand;
import jaypasha.owilgrief.manageraddons.commands.TrollingPlayersDamageAdmin;
import jaypasha.owilgrief.manageraddons.utils.ColorManager;
import jaypasha.owilgrief.manageraddons.utils.Tools;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.management.monitor.StringMonitor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Events implements Listener {

    private Map<UUID, Long> lastMessageTime = new HashMap<>();
    private int messageInterval = 140;
    private final Map<UUID, Boolean> playerOnItem = new HashMap<>();
    String title_gab = ColorManager.Goto(MainClass.getInstance().getConfig().getString("title-pickupgab"));
    String title_fly = ColorManager.Goto(MainClass.getInstance().getConfig().getString("title-pickupfly"));

    @EventHandler
    public void BlockBreakGab(BlockBreakEvent e) {
        Player player1 = e.getPlayer();
        UUID player = e.getPlayer().getUniqueId();
            if (GabCommand.gabPlayers.contains(player)) {
                player1.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("BlockBreakGab")));
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockPlaceGab(BlockPlaceEvent e) {
        Player player1 = e.getPlayer();
        UUID player = e.getPlayer().getUniqueId();
            if (GabCommand.gabPlayers.contains(player)) {
                player1.sendMessage(ColorManager.Goto(MainClass.getInstance().getConfig().getString("BlockPlaceGab")));
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void PickUpFly(PlayerAttemptPickupItemEvent e) {
        UUID player = e.getPlayer().getUniqueId();
            if (GabCommand.gabPlayers.contains(player)) {
            } else {
                if (FlyCommand.flyPlayers.contains(player))
                    e.setCancelled(true);
        }
    }

    @EventHandler
    public void PickUpGab(PlayerAttemptPickupItemEvent e) {
        UUID player = e.getPlayer().getUniqueId();
            if (GabCommand.gabPlayers.contains(player)) {
                e.setCancelled(true);
            }
    }
    @EventHandler
    public void onPlayerMoveFly(PlayerMoveEvent event) {
        if (MainClass.getInstance().getConfig().getBoolean("ItemPickupMessageFly")) {
            Player player = event.getPlayer();
            Location playerLocation = player.getLocation();
                boolean onFlyItemFlag = false;
                boolean onGabItemFlag = false;

                for (Item item : playerLocation.getWorld().getEntitiesByClass(Item.class)) {
                    Location itemLocation = item.getLocation();
                    double distance = playerLocation.distance(itemLocation);
                    if (distance <= 1.0) {
                        if (FlyCommand.flyPlayers.contains(player.getUniqueId())) {
                            onFlyItemFlag = true;
                        }
                        if (GabCommand.gabPlayers.contains(player.getUniqueId())) {
                            onGabItemFlag = true;
                        }
                        break;
                    }
                }
                if (onFlyItemFlag) {
                    sendFlyMessage(player);
                } else if (onGabItemFlag) {
                    sendGabMessage(player);
                } else {
                    playerOnItem.put(player.getUniqueId(), false);
                }
        } else {
            event.setCancelled(false);
        }
    }

    private void sendFlyMessage(Player player) {
        long currentTime = System.currentTimeMillis() / 50;
        if (!playerOnItem.getOrDefault(player.getUniqueId(), false) && (!lastMessageTime.containsKey(player.getUniqueId()) || currentTime - lastMessageTime.get(player.getUniqueId()) >= messageInterval)) {
            player.sendMessage(ColorManager.Goto(Tools.getInstance().getConfig().getString("PickUpMessageFly")));
            lastMessageTime.put(player.getUniqueId(), currentTime);
            player.sendTitle(title_fly, null, 25, 45, 25);
        }
        playerOnItem.put(player.getUniqueId(), true);
    }

    private void sendGabMessage(Player player) {
        long currentTime = System.currentTimeMillis() / 50;
        if (!playerOnItem.getOrDefault(player.getUniqueId(), false) && (!lastMessageTime.containsKey(player.getUniqueId()) || currentTime - lastMessageTime.get(player.getUniqueId()) >= messageInterval)) {
            player.sendMessage(ColorManager.Goto(Tools.getInstance().getConfig().getString("PickUpMessageGab")));
            lastMessageTime.put(player.getUniqueId(), currentTime);
            player.sendTitle(title_gab, null, 25, 45, 25);
        }
        playerOnItem.put(player.getUniqueId(), true);
    }
    @EventHandler
    public void onPlayerDamageGab(EntityDamageByEntityEvent e) {
            UUID player = e.getDamager().getUniqueId();
            if (GabCommand.gabPlayers.contains(player)) {
                e.setCancelled(true);
            }
    }

    @EventHandler
    public void onPlayerDamageFly(EntityDamageByEntityEvent e) {
        UUID player = e.getDamager().getUniqueId();
        if (FlyCommand.flyPlayers.contains(player)) {
            FlyCommand.flyPlayers.remove(player);
        }
    }

        @EventHandler
        public void PlayerTakeDamageGab (EntityDamageEvent e) {
            UUID player = e.getEntity().getUniqueId();
                if (GabCommand.gabPlayers.contains(player)) {
                    e.setCancelled(true);
                }
        }
        @EventHandler
        public void PlayerTakeDamageFly (EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        UUID player = e.getEntity().getUniqueId();
        if (FlyCommand.flyPlayers.contains(player)) {
            if (damager instanceof Player) {
                FlyCommand.flyPlayers.remove(player);
            }
        }
    }
    @EventHandler
    public void onTakeDamage(EntityDamageEvent e) {
        UUID target = e.getEntity().getUniqueId();
        if (TrollingPlayersDamageAdmin.trollingDamagePlayers.contains(target)) {
            e.setDamage(e.getDamage()*2);
        }
    }
    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        Player player1 = e.getPlayer();
        if (FlyCommand.flyPlayers.contains(player)) {
            player1.setAllowFlight(true);
            player1.setFlying(true);
        }
    }
}
