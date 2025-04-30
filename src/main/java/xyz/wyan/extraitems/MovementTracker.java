package xyz.wyan.extraitems;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import xyz.wyan.extraitems.DataTypes.CappedLinkedHashMap;
import xyz.wyan.extraitems.DataTypes.CappedLinkedList;

import java.util.HashMap;
import java.util.UUID;

import static xyz.wyan.extraitems.ExtraItems.instance;

public class MovementTracker implements Listener {
    public static HashMap<UUID, CappedLinkedList<Location>> locations = new HashMap<>();
    public static boolean registered = false;


    public static void startLocations() {
        new Thread(() -> {
            while (registered) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                instance.getServer().getOnlinePlayers().forEach(player -> {
                    UUID playerId = player.getUniqueId();
                    Location currentLocation = player.getLocation();

                    CappedLinkedList<Location> playerHistory = locations.computeIfAbsent(playerId, id -> new CappedLinkedList<>(10));
                    playerHistory.addLast(currentLocation);
                    locations.put(playerId, playerHistory);
                });
            }
        }).start();
    }
}
