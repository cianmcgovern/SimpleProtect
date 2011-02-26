package com.cianmcgovern.simpleprotect;

import java.util.ArrayList;
import org.bukkit.block.Block;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;


public class SimpleProtectBlockListener extends BlockListener {

    private final SimpleProtect plugin;
    private final int[] BANNED_BLOCKS = {46};

    public SimpleProtectBlockListener(SimpleProtect plugin) {
        this.plugin = plugin;
    }

    public void onBlockDamage(BlockDamageEvent event) {
        if (!plugin.protectors.contains(event.getPlayer().getName().toLowerCase())) {
            if (event.getDamageLevel() == BlockDamageLevel.BROKEN) {
                Block b = event.getBlock();
                int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
                ArrayList<String> f = plugin.playerListener.getProtection(coords);
                if (!(f == null || f.contains(event.getPlayer().getName()))) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("Protected area.  Cannot destroy.");
                }
            }
        }
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        if (!plugin.protectors.contains(event.getPlayer().getName().toLowerCase())) {
            Block b = event.getBlock();
            int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
            ArrayList<String> f = plugin.playerListener.getProtectionOffset(coords, isBanned(event.getBlock().getTypeId()) ? 10 : 0);
            if (!(f == null || f.contains(event.getPlayer().getName()))) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("Protected area.  Cannot build here.");
            }
        }
    }

    public void onBlockFlow(BlockFromToEvent event) {
        Block b = event.getBlock();
        int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
        ArrayList<String> f = plugin.playerListener.getProtectionOffset(coords,1);
        if (!(f == null)) {
            event.setCancelled(true);
        }
    }

    public void onBlockIgnite(BlockIgniteEvent event) {
        Player player = null;
        if (event.getPlayer() != null)
            player = event.getPlayer();
        if (!plugin.protectors.contains((player == null ? "foo" : player.getName().toLowerCase()))) {
            Block b = event.getBlock();
            int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
            ArrayList<String> f = plugin.playerListener.getProtectionOffset(coords,1);

            if (!(f == null || f.contains((player == null ? "foo" : player.getName())))) {
                event.setCancelled(true);
                if (player != null)
                    player.sendMessage("Protected area.  Cannot ignite here.");
            }
        }
    }
    
    private boolean isBanned(int id) {
        for (int z : this.BANNED_BLOCKS) {
            if (z == id)
                return true;
        }
        return false;
            
    }
}
