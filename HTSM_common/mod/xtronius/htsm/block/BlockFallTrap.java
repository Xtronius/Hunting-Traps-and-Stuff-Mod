package mod.xtronius.htsm.block;

import mod.xtronius.htsm.core.HTSM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFallTrap extends HTSMBlock {

	public BlockFallTrap() {
		super(Material.leaves);
		this.setBlockName("BlockFallTrap");
	}
	
	private void fall(World world, int x, int y, int z, Entity entity, ForgeDirection dir) {
		if (isOverridable(world, x, y - 1, z) && y >= 0) {
	        byte radius = 32;
	
	        if (world.checkChunksExist(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius)) {
	            if (!world.isRemote) {
	                EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this, world.getBlockMetadata(x, y, z));
	                world.spawnEntityInWorld(entityfallingblock);
	                
	                world.setBlockMetadataWithNotify(x, y, z, 1, 2);
	                
	                Block north =  world.getBlock(x, y, z-1);
	                Block south =  world.getBlock(x, y, z+1);
	                Block east =  world.getBlock(x+1, y, z);   
	                Block west =  world.getBlock(x-1, y, z);
	                
	                BlockFallTrap blockNorth = north != null && north instanceof BlockFallTrap ? (BlockFallTrap)north : null;
	                BlockFallTrap blockSouth = south != null && south instanceof BlockFallTrap ? (BlockFallTrap)south : null;
	                BlockFallTrap blockEast = east != null && east instanceof BlockFallTrap ? (BlockFallTrap)east : null;
	                BlockFallTrap blockWest = west != null && west instanceof BlockFallTrap ? (BlockFallTrap)west : null;
	                
	                if(blockNorth != null && world.getBlockMetadata(x, y, z-1) == 0)
	                	blockNorth.fall(world, x, y, z-1, entity, ForgeDirection.NORTH);
	                if(blockSouth != null && world.getBlockMetadata(x, y, z+1) == 0)
	                	blockSouth.fall(world, x, y, z+1, entity, ForgeDirection.SOUTH);
	                if(blockEast != null && world.getBlockMetadata(x+1, y, z) == 0)
	                	blockEast.fall(world, x+1, y, z, entity, ForgeDirection.WEST);
	                if(blockWest != null && world.getBlockMetadata(-1, y, z) == 0)
	                	blockWest.fall(world, x-1, y, z, entity, ForgeDirection.EAST);
	            }
	        }
	        else {
	            world.setBlockToAir(x, y, z);
	
	            while (isOverridable(world, x, y - 1, z) && y > 0) --y;
	            if (y > 0)  world.setBlock(x, y, z, this);
	        }
        }
	}
	
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {

		if(!entity.isSneaking()) {
			if (isOverridable(world, x, y - 1, z) && y >= 0) {
		        byte radius = 32;
		
		        if (world.checkChunksExist(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius)) {
		            if (!world.isRemote) {
		                EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this, world.getBlockMetadata(x, y, z));
		                world.spawnEntityInWorld(entityfallingblock);
		                
		                world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		                
		                
		                Block north =  world.getBlock(x, y, z-1);
		                Block south =  world.getBlock(x, y, z+1);
		                Block east =  world.getBlock(x+1, y, z);   
		                Block west =  world.getBlock(x-1, y, z);
		                
		                BlockFallTrap blockNorth = north != null && north instanceof BlockFallTrap ? (BlockFallTrap)north : null;
		                BlockFallTrap blockSouth = south != null && south instanceof BlockFallTrap ? (BlockFallTrap)south : null;
		                BlockFallTrap blockEast = east != null && east instanceof BlockFallTrap ? (BlockFallTrap)east : null;
		                BlockFallTrap blockWest = west != null && west instanceof BlockFallTrap ? (BlockFallTrap)west : null;
		                
		                if(blockNorth != null && world.getBlockMetadata(x, y, z-1) == 0)
		                	blockNorth.fall(world, x, y, z-1, entity, ForgeDirection.SOUTH);
		                if(blockSouth != null && world.getBlockMetadata(x, y, z+1) == 0)
		                	blockSouth.fall(world, x, y, z+1, entity, ForgeDirection.NORTH);
		                if(blockEast != null && world.getBlockMetadata(x+1, y, z) == 0)
		                	blockEast.fall(world, x+1, y, z, entity, ForgeDirection.WEST);
		                if(blockWest != null && world.getBlockMetadata(x-1, y, z) == 0)
		                	blockWest.fall(world, x-1, y, z, entity, ForgeDirection.EAST);
		            }
		        }
		        else {
		            world.setBlockToAir(x, y, z);
		
		            while (isOverridable(world, x, y - 1, z) && y > 0) --y;
		            if (y > 0)  world.setBlock(x, y, z, this);
		        }
	        }
		}
	} 
	
	public static boolean isOverridable(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        if (block.isAir(world, x, y, z)) {
            return true;
        }
        else if (block == Blocks.fire) {
            return true;
        }
        else {
            Material material = block.getMaterial();
            return material == Material.water ? true : material == Material.lava;
        }
    }

}
