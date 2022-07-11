package de.cfp.chestlocker;

import org.bukkit.Bukkit;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    public Chestlocker main;

    public Events(Chestlocker main) {
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(!Chestlocker.lockable(e.getBlock().getType())) return;
        Container c = (Container)e.getBlock().getState();
        if(!c.isLocked()) return;
        Player p = e.getPlayer();
        for(int i = 0 ; i < p.getInventory().getSize() ; i++) {
            ItemStack is = p.getInventory().getItem(i);
            if(is == null) continue;
            if(!is.getItemMeta().getDisplayName().startsWith("§f")) continue;
            if(!c.getLock().equals(is.getItemMeta().getDisplayName())) continue;
            if(is.getItemMeta() == null || is.getItemMeta().getDisplayName().equals("")) continue;
            String lock = is.getItemMeta().getDisplayName();
            if(!c.getLock().equals(lock)) continue;
            return;
        }
        p.sendMessage("§cYou need to have the key to break this block");
        e.setCancelled(true);
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!Chestlocker.lockable(e.getClickedBlock().getType())) return;
        Container c = (Container)e.getClickedBlock().getState();
        if(!c.isLocked()) return;
        Player p = e.getPlayer();
        for(int i = 0 ; i < p.getInventory().getSize() ; i++) {
            ItemStack is = p.getInventory().getItem(i);
            if(is == null) continue;
            if(!is.getItemMeta().getDisplayName().startsWith("§f")) continue;
            if(!c.getLock().equals(is.getItemMeta().getDisplayName())) continue;
            ItemStack item = p.getInventory().getItemInMainHand();
            p.getInventory().setItemInMainHand(is);
            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run() {
                    p.getInventory().setItemInMainHand(item);
                }
            }, 1);
            return;
        }
    }

}
