package de.mrchick.bukkit.secretdoors;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class SecretDoorsBlockListener extends BlockListener
{
    private SecretDoors plugin = null;
    
    public SecretDoorsBlockListener(SecretDoors plugin)
    {
        this.plugin = plugin;
    }
    
    @Override
    public void onBlockBreak(BlockBreakEvent bbe)
    {
        if(plugin.isSecretDoor(bbe.getBlock()))
            plugin.closeDoor(bbe.getBlock());
    }
}
