package de.cfp.chestlocker;

import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Events implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(!Chestlocker.lockable(e.getBlock().getType())) return;
        Container c = (Container)e.getBlock().getState();
        if(!c.isLocked()) return;
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand().getItemMeta() == null || p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("")) {
            p.sendMessage("§cYou need to hold the key to break this block");
            e.setCancelled(true);
            return;
        }
        String lock = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        if(!c.getLock().equals(lock)) {
            p.sendMessage("§cYou need to hold the key to break this block");
            e.setCancelled(true);
        }
    }

}
