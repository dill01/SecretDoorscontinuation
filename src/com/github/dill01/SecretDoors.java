package com.github.dill01;

import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretDoors extends JavaPlugin
{
    private HashMap<Block, SecretDoor> doors = new HashMap<Block, SecretDoor>();
    
    @Override
    public void onDisable()
    {
        for(Block door : this.doors.keySet())
            this.doors.get(door).close();
            
        System.out.println(this.getDescription().getFullName() + " by dill01 disabled");
    }
    
    @Override
    @EventHandler (priority = EventPriority.MONITOR)
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SecretDoorsPlayerListener(this), this);
        
        System.out.println(this.getDescription().getFullName() + " by Dill01 enabled");
    }
    
    public SecretDoor addDoor(SecretDoor door)
    {
        this.doors.put(door.getKey(), door);
        return door;
    }
    
    public boolean isSecretDoor(Block door)
    {
        return this.doors.containsKey(door);
    }
    
    public void closeDoor(Block door)
    {
        if(isSecretDoor(door))
        {
            this.doors.get(door).close();
            this.doors.remove(door);
        }
    }
}
