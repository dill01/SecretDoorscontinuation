package com.github.dill01;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;

public class SecretDoor
{
    private Block[]    doorblocks = new Block[2];
    private Block[]    blocks     = new Block[2];
    private Material[] materials  = new Material[2];
    private byte[]     data       = new byte[2];
    private Direction  direction  = null;
    
    public SecretDoor(Block door, Block other, Direction direction)
    {
        if (castBlockToDoor(door).isTopHalf())
        {
            this.doorblocks[0] = door;
            this.doorblocks[1] = door.getRelative(BlockFace.DOWN);
            
            this.blocks[0] = other;
            this.blocks[1] = other.getRelative(BlockFace.DOWN);
        }
        else
        {
            this.doorblocks[1] = door;
            this.doorblocks[0] = door.getRelative(BlockFace.UP);
            
            this.blocks[1] = other;
            this.blocks[0] = other.getRelative(BlockFace.UP);
        }
        
        this.materials[0] = this.blocks[0].getType();
        this.materials[1] = this.blocks[1].getType();
        
        this.data[0] = this.blocks[0].getData();
        this.data[1] = this.blocks[1].getData();
        
        this.direction = direction;
    }
    
    private static Door castBlockToDoor(Block block)
    {
        Door door = null;
        
        if (block.getState().getData() instanceof Door)
            door = (Door) block.getState().getData();
        
        return door;
        
    }
    
    public static boolean isAdjacentDoor(Block doorBlock, BlockFace face)
    {
        boolean state = false;
        
        if (doorBlock.getState().getData() instanceof Door)
        {
            Door door = castBlockToDoor(doorBlock);
            switch (face)
            {
                case WEST:
                    state = BlockFace.SOUTH_EAST == door.getHingeCorner();
                    break;
                case EAST:
                    state = BlockFace.NORTH_WEST == door.getHingeCorner();
                    break;
                case SOUTH:
                    state = BlockFace.NORTH_EAST == door.getHingeCorner();
                    break;
                case NORTH:
                    state = BlockFace.SOUTH_WEST == door.getHingeCorner();
                    break;
            }
        }
        return state;
    }
    
    public static boolean isDoubleDoor(Block block)
    {
        boolean state = false;
        
        Block[] blocks = { block.getRelative(BlockFace.EAST), block.getRelative(BlockFace.NORTH), block.getRelative(BlockFace.WEST), block.getRelative(BlockFace.SOUTH) };
        for (Block b : blocks)
            if (Material.WOODEN_DOOR.equals(b.getType()))
                state = true;
        
        return state;
    }
    
    public Block getKey()
    {
        return this.doorblocks[0];
    }
    
    public void close()
    {
        for (int i = 0; i < 2; i++)
        {
            this.blocks[i].setType(this.materials[i]);
            this.blocks[i].setData(this.data[i]);
        }
    }
    
    public void open()
    {
        for (int i = 0; i < 2; i++)
        {
            this.blocks[i].setType(Material.AIR);
            if (this.direction == Direction.BLOCK_FIRST)
                this.doorblocks[i].setData((byte) (this.doorblocks[i].getData() | 0x4));
        }
    }
    
    public static Block getKeyFromBlock(Block block)
    {
        
        Block door = null;
        
        if (block.getState().getData() instanceof Door)
        {
            if (castBlockToDoor(block).isTopHalf())
                door = block;
            else
                door = block.getRelative(BlockFace.UP);
        }
        
        return door;
    }
    
    public enum Direction
    {
        BLOCK_FIRST, DOOR_FIRST
    }
}
