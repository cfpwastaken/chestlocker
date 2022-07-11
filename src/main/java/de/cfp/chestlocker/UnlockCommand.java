package de.cfp.chestlocker;

import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou need to be a player to execute this command.");
            return true;
        }

        Player p = (Player)sender;
        Block b = p.getTargetBlock(20);
        if(b == null) {
            p.sendMessage("§cYou need to look at a block");
            return true;
        }
        if(!Chestlocker.lockable(b.getType())) {
            p.sendMessage("§cYou can not unlock this block");
            return true;
        }
        if(p.getInventory().getItemInMainHand().getItemMeta() == null) {
            p.sendMessage("§cYou need to hold the key");
            return true;
        }
        String lock = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        Container c = (Container) b.getState();
        if(lock.equals("")) {
            p.sendMessage("§cYour key needs to be named");
            return true;
        }
        if(!c.isLocked()) {
            p.sendMessage("§cThe block is not locked");
            return true;
        }
        if(!c.getLock().equals(lock)) {
            p.sendMessage("§cYou need to hold the key");
            return true;
        }
        c.setLock("");
        c.update();
        p.sendMessage("§aUnlocked");
        return true;
    }

}
