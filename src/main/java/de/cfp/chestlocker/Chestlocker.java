package de.cfp.chestlocker;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class Chestlocker extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("lock").setExecutor(new LockCommand());
        getCommand("unlock").setExecutor(new UnlockCommand());
        getCommand("key").setExecutor(new KeyCommand());
        getServer().getPluginManager().registerEvents(new Events(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean lockable(Material mat) {
        return mat.equals(Material.CHEST) || mat.equals(Material.HOPPER) || mat.equals(Material.BLAST_FURNACE) || mat.equals(Material.FURNACE) || mat.equals(Material.TRAPPED_CHEST) || mat.equals(Material.SMOKER);
    }

}
