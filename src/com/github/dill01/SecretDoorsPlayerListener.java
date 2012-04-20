package com.github.dill01;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SecretDoorsPlayerListener implements Listener
{
    private SecretDoors plugin = null;
    
    public SecretDoorsPlayerListener(SecretDoors plugin)
    {
        this.plugin = plugin;
    }
    
    private boolean isValidBlock(Block block)
    {
        boolean ret = true;
        
        if (block != null)
            switch (block.getType())
            {
                case CHEST:
                case FURNACE:
                case DISPENSER:
                case WORKBENCH:
                case WOOD_PLATE:
                case STONE_PLATE:
                    ret = false;
                    break;
            }
        else
            ret = false;
        
        return ret;
    }
    
    public void onPlayerInteract(PlayerInteractEvent pie)
    {
        if (Action.LEFT_CLICK_BLOCK.equals(pie.getAction()) || (isValidBlock(pie.getClickedBlock()) && Action.RIGHT_CLICK_BLOCK.equals(pie.getAction())))
        {
            Block clicked = pie.getClickedBlock();
            Block behind = clicked.getRelative(pie.getBlockFace().getOppositeFace());
            
            SecretDoor door = null;
            
            if (Material.WOODEN_DOOR.equals(clicked.getType()) && !SecretDoor.isDoubleDoor(clicked))
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
            else if (Material.WOODEN_DOOR.equals(behind.getType()) && !SecretDoor.isDoubleDoor(behind))
            {
                if (this.plugin.isSecretDoor(SecretDoor.getKeyFromBlock(behind)))
                {
                    this.plugin.closeDoor(SecretDoor.getKeyFromBlock(behind));
                }
                else if (SecretDoor.isAdjacentDoor(behind, pie.getBlockFace().getOppositeFace()))
                    door = new SecretDoor(behind, clicked, SecretDoor.Direction.BLOCK_FIRST);
            }
            
            if (!(door == null))
            {
                plugin.addDoor(door).open();
                pie.getPlayer().sendMessage(ChatColor.RED + "Don't forget to close the door!");
            }
        }
    }}

