package com.github.dill01;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
	
public class SecretDoorsBlockListener implements Listener
{
    private SecretDoors plugin = null;
    
    public SecretDoorsBlockListener(SecretDoors plugin)
    {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe)
    {
        if(plugin.isSecretDoor(bbe.getBlock()))
            plugin.closeDoor(bbe.getBlock());
    }
}
