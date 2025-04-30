package xyz.wyan.extraitems;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static xyz.wyan.extraitems.ExtraItems.instance;

public class CustomRecipes {
    public static HashMap<String, NamespacedKey> keys = new HashMap<>();

    public static ShapedRecipe recall(){
        NamespacedKey key = new NamespacedKey(instance, "Recall");
        if(keys.containsKey("Recall")){
            return null;
        }
        keys.put("Recall", key);
        ItemStack recall = ItemStack.of(Material.ENDER_PEARL);
        ItemMeta meta = recall.getItemMeta();
        meta.displayName(Component.text("§bRecall"));
        List<Component> lore = meta.lore();
        if(lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(Component.text("Right click to teleport back 10 seconds"));
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "Recall");
        meta.setRarity(ItemRarity.UNCOMMON);
        recall.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(key, recall);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.ECHO_SHARD);
        recipe.setIngredient('B', Material.ENDER_PEARL);
        return recipe;
    }

    public static ShapedRecipe echoShard(){
        NamespacedKey key = new NamespacedKey(instance, "EchoShard");
        if(keys.containsKey("EchoShard")){
            return null;
        }
        keys.put("EchoShard", key);
        ItemStack echoShard = ItemStack.of(Material.ECHO_SHARD);

        ShapedRecipe recipe = new ShapedRecipe(key, echoShard);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.SCULK);
        recipe.setIngredient('B', Material.AMETHYST_SHARD);
        return recipe;
    }

    public static ShapedRecipe experienceBottle(){
        NamespacedKey key = new NamespacedKey(instance, "ExperienceBottle");
        if(keys.containsKey("ExperienceBottle")){
            return null;
        }
        keys.put("ExperienceBottle", key);

        ItemStack expBottle = ItemStack.of(Material.EXPERIENCE_BOTTLE);

        ItemMeta meta = expBottle.getItemMeta();
        meta.displayName(Component.text("§bExperience Bottle"));
        List<Component> lore = meta.lore();
        if(lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(Component.text("Right click: Store experience"));
        lore.add(Component.text("Left click: Use experience"));
        lore.add(Component.text("Experience: 0"));
        lore.add(Component.text("§e--------------------"));
        meta.lore(lore);
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 0);
        meta.setRarity(ItemRarity.UNCOMMON);
        expBottle.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(key,expBottle);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.LAPIS_BLOCK);
        recipe.setIngredient('B', Material.GLASS_BOTTLE);
        return recipe;
    }
}
