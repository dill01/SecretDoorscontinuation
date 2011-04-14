package de.mrchick.bukkit.secretdoors;

import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretDoors extends JavaPlugin
{
    private HashMap<Block, SecretDoor> doors = new HashMap<Block, SecretDoor>();
    
    @Override
    public void onDisable()
    {
        for(Block door : this.doors.keySet())
            this.doors.get(door).close();
            
        System.out.println(this.getDescription().getFullName() + " by MrChick disabled");
    }
    
    @Override
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT, new SecretDoorsPlayerListener(this), Priority.Monitor, this);
        this.getServer().getPluginManager().registerEvent(Type.BLOCK_BREAK, new SecretDoorsBlockListener(this), Priority.Monitor, this);
        
        System.out.println(this.getDescription().getFullName() + " by MrChick enabled");
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
