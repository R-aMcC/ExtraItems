package xyz.wyan.extraitems;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ExtraItems extends JavaPlugin {
    public static ExtraItems instance;
    public static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        logger = getLogger();
        MovementTracker.registered = true;
        getServer().addRecipe(CustomRecipes.recall());
        MovementTracker.startLocations();
        getServer().addRecipe(CustomRecipes.echoShard());
        getServer().addRecipe(CustomRecipes.experienceBottle());
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
    }

    @Override
    public void onDisable() {
        MovementTracker.registered = false;

    }
}
