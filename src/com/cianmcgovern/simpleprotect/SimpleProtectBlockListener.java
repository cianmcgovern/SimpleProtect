package com.cianmcgovern.simpleprotect;

import java.util.ArrayList;
import org.bukkit.block.Block;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;


public class SimpleProtectBlockListener extends BlockListener {

    private final SimpleProtect plugin;

    public SimpleProtectBlockListener(SimpleProtect plugin) {
        this.plugin = plugin;
    }

    public void onBlockBreak(BlockBreakEvent event) {
        if (!plugin.protectors.contains(event.getPlayer().getName().toLowerCase()) || event.getPlayer().isOp()==true) {
            if (!event.isCancelled()) {
                Block b = event.getBlock();
                int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
                ArrayList<String> f = SimpleProtectPlayerListener.getProtection(coords);
                if (!(f == null || f.contains(event.getPlayer().getName()))) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("Protected area. Cannot destroy.");
                }
            }
        }
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        if (!plugin.protectors.contains(event.getPlayer().getName().toLowerCase()) || event.getPlayer().isOp()==true) {
            Block b = event.getBlock();
            int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
            ArrayList<String> f = SimpleProtectPlayerListener.getProtection(coords);
            if (!(f == null || f.contains(event.getPlayer().getName()))) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("Protected area.  Cannot build here.");
            }
       }
    }

    public void onBlockFlow(BlockFromToEvent event) {
        Block b = event.getBlock();
        int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
        ArrayList<String> f = SimpleProtectPlayerListener.getProtectionOffset(coords,1);
        if (!(f == null)) {
            event.setCancelled(true);
        }
    }

    public void onBlockIgnite(BlockIgniteEvent event) {
        Player player = null;
        if (event.getPlayer() != null)
            player = event.getPlayer();
        if (!plugin.protectors.contains((player == null ? "foo" : player.getName().toLowerCase())) || event.getPlayer().isOp()==true) {
            Block b = event.getBlock();
            int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
            ArrayList<String> f = SimpleProtectPlayerListener.getProtectionOffset(coords,1);

            if (!(f == null || f.contains((player == null ? "foo" : player.getName())))){
            	event.setCancelled(true);
            	if(event.getPlayer()!=null)
            		player.sendMessage("Protected area.  Cannot ignite here.");
            }
        }
    }
    
    public void onBlockBurn(BlockBurnEvent event) {
        
            Block b = event.getBlock();
            int[] coords = {b.getX(), b.getY() + 1, b.getZ()};
            ArrayList<String> f = SimpleProtectPlayerListener.getProtectionOffset(coords,1);
            if (!(f == null || f.contains(coords))) {
                event.setCancelled(true);
                System.out.println("Inside burn cancel");
            }
            
            
    }
    
}
