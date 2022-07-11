package de.cfp.chestlocker;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KeyCommand implements CommandExecutor {

    public String randomString() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou need to be a player to execute this command.");
            return true;
        }

        Player p = (Player)sender;

        if(p.getInventory().getItemInMainHand().getItemMeta() == null) {
            p.sendMessage("§cYou need to hold the key");
            return true;
        }
        String lock = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        if(lock.equals("")) {
            p.sendMessage("§cYour key needs to be named");
            return true;
        }

        if(args.length != 1) {
            p.sendMessage("§cUsage: /key <create|copy>");
            return true;
        }

        switch(args[0]) {
            case "create":
                if(lock.startsWith("§f")) {
                    p.sendMessage("§cThis is already a key");
                    return true;
                }
                if(p.getLevel() < 2) {
                    p.sendMessage("§cYou need 2 XP Levels to create a key");
                    return true;
                }
                ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
                meta.setDisplayName("§f" + p.getUniqueId() + "-" + randomString());
                List<String> lore = new ArrayList<>();
                lore.add("Key");
                lore.add(lock);
                meta.setLore(lore);
                p.getInventory().getItemInMainHand().setItemMeta(meta);
                p.setLevel(p.getLevel() - 2);
                p.sendMessage("§aMade a new key");
                break;
            case "copy":
                if(!lock.startsWith("§f")) {
                    p.sendMessage("§cThis is not a key");
                    return true;
                }
                if(p.getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
                    p.sendMessage("§cHold a key to copy to in your offhand");
                    return true;
                }
                if(p.getInventory().getItemInOffHand().getType().equals(p.getInventory().getItemInMainHand().getType())) {
                    p.getInventory().getItemInOffHand().setItemMeta(p.getInventory().getItemInMainHand().getItemMeta());
                } else {
                    ItemMeta offmeta = p.getInventory().getItemInOffHand().getItemMeta();
                    offmeta.setDisplayName(lock);
                    List<String> keylore = new ArrayList<>();
                    keylore.add("Key");
                    keylore.add(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(2));
                    offmeta.setLore(keylore);
                    p.getInventory().getItemInOffHand().setItemMeta(offmeta);
                }
                p.sendMessage("§aKey copied");
                break;
            default:
                p.sendMessage("§cUsage: /key <create|copy>");
                break;
        }

        return true;
    }

}
