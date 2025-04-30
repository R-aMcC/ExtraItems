package xyz.wyan.extraitems;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.wyan.extraitems.DataTypes.CappedLinkedHashMap;
import xyz.wyan.extraitems.DataTypes.CappedLinkedList;
import xyz.wyan.extraitems.Utils.Experience;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static xyz.wyan.extraitems.ExtraItems.instance;
import static xyz.wyan.extraitems.ExtraItems.logger;
public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        /*
        if(!e.getAction().isRightClick()){
            //logger.info("Returning: Not right click ( "+ e.getAction().name() + " )");
            return;
        }
         */
        ItemStack item = e.getItem();
        if(item == null){
            //logger.info("Returning: Item is null");
            return;
        }
        if(CustomRecipes.keys.isEmpty()){
            //logger.info("Returning: No keys found");
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if(meta == null){
            //logger.info("Returning: ItemMeta is null");
            return;
        }
        List<Component> lore = meta.lore();
        boolean doReturn = true;
        String foundKey = null;
        NamespacedKey key = null;
        for(HashMap.Entry<String, NamespacedKey> entry : CustomRecipes.keys.entrySet()){
            if(meta.getPersistentDataContainer().has(entry.getValue())){
                doReturn = false;
                foundKey = entry.getKey();
                key = entry.getValue();
                break;
            }
        }
        if(doReturn){
            //logger.info("Returning: No key found");
            return;
        }
        Player player = e.getPlayer();
        switch(foundKey){
            case "Recall" -> {
                CappedLinkedList<Location> playerHistory = MovementTracker.locations.get(e.getPlayer().getUniqueId());
                if(playerHistory == null){
                    //logger.info("Returning: No player history found");
                    return;
                }
                player.teleport(playerHistory.getFirst());
                //logger.info("Returning: Teleporting player and canceling event");
                e.setCancelled(true);
            } default -> {
                //logger.info("Returning: No action for key " + foundKey);
                return;
            }
            case "ExperienceBottle" ->{
                e.setCancelled(true);
                int exp;
                try{
                    exp = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
                }catch (NumberFormatException ex) {
                    logger.info("Something went wrong");
                    logger.info("Key: " + meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER));
                    player.sendMessage("§cSomething went wrong with the experience bottle");
                    e.setCancelled(true);
                    return;
                }

                int level = player.getLevel();
                //logger.info("Level: " + level);
                float progress = player.getExp();
                //logger.info("Progress: " + progress);
                int totalExp = Experience.getTotalExperience(level, progress);
                //logger.info("Total exp: " + totalExp);

                int newTotalExp = -1;
                int finalLevel = -1;
                float finalProgress = -1;

                switch(e.getAction()){
                    case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                        if(totalExp>=100){
                            newTotalExp = totalExp-100;
                            finalLevel = Experience.getLevelFromExperience(newTotalExp);
                            finalProgress = Experience.getProgressFromExperience(newTotalExp, finalLevel);
                            //logger.info("Total exp: " + totalExp);
                            //logger.info("Final level: " + finalLevel);
                            //logger.info("Final progress: " + finalProgress);
                            exp += 100;
                            meta.getPersistentDataContainer().set(key, org.bukkit.persistence.PersistentDataType.INTEGER, exp);
                            lore.set(2, Component.text("Experience: " + exp));
                            // Also add the increase the little bar thing
                            meta.lore(lore);
                            item.setItemMeta(meta);
                        }else{
                            player.sendMessage("§cYou don't have enough experience to store");
                        }
                    }
                    case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> {}

                }
                if(newTotalExp != -1){
                    player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1, 1);
                    player.setExp(finalProgress);
                    player.setLevel(finalLevel);
                }
            }
        }



    }
}
