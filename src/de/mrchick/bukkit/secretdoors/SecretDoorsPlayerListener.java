package de.mrchick.bukkit.secretdoors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class SecretDoorsPlayerListener extends PlayerListener
{
    private SecretDoors plugin = null;
    
    public SecretDoorsPlayerListener(SecretDoors plugin)
    {
        this.plugin = plugin;
    }
    
    @Override
    public void onPlayerInteract(PlayerInteractEvent pie)
    {
        if (Action.LEFT_CLICK_BLOCK.equals(pie.getAction()) || Action.RIGHT_CLICK_BLOCK.equals(pie.getAction()))
        {
            Block clicked = pie.getClickedBlock();
            Block behind = clicked.getRelative(pie.getBlockFace().getOppositeFace());
            
            SecretDoor door = null;
            
            if (Material.WOODEN_DOOR.equals(clicked.getType()))
            {
                if (this.plugin.isSecretDoor(SecretDoor.getKeyFromBlock(clicked)))
                {
                    this.plugin.closeDoor(SecretDoor.getKeyFromBlock(clicked));
                }
                else if (!Material.AIR.equals(behind.getType()))
                {
                    if (SecretDoor.isAdjacentDoor(clicked, pie.getBlockFace()))
                        door = new SecretDoor(clicked, behind, SecretDoor.Direction.DOOR_FIRST);
                }
                
            }
            else if (Material.WOODEN_DOOR.equals(behind.getType()))
            {
                if (this.plugin.isSecretDoor(SecretDoor.getKeyFromBlock(behind)))
                {
                    this.plugin.closeDoor(SecretDoor.getKeyFromBlock(behind));
                }
                else if (SecretDoor.isAdjacentDoor(behind, pie.getBlockFace().getOppositeFace()))
                    door = new SecretDoor(behind, clicked, SecretDoor.Direction.BLOCK_FIRST);
            }
            
            if (!(door == null))
                plugin.addDoor(door).open();
        }
    }
}
