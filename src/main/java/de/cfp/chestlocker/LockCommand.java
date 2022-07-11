package de.cfp.chestlocker;

import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LockCommand implements CommandExecutor {

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
            p.sendMessage("§cYou can not lock this block");
            return true;
        }
        if(p.getInventory().getItemInMainHand().getItemMeta() == null) {
            p.sendMessage("§cYou need to hold the key");
            return true;
        }
        String lock = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        if(lock.equals("")) {
            p.sendMessage("§cYour key needs to be named");
            return true;
        }
        Container c = (Container) b.getState();
        if(c.isLocked()) {
            p.sendMessage("§cThis block is already locked");
            return true;
        }
        if(!lock.startsWith("§f")) {
            p.sendMessage("§cThis is not a key. Make it a key with /key create");
            return true;
        }
        c.setLock(lock);
        c.update();
        p.sendMessage("§aLocked");
        return true;
    }

}
