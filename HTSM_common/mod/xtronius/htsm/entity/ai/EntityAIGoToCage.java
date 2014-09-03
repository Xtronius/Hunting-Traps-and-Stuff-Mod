package mod.xtronius.htsm.entity.ai;

import mod.xtronius.htsm.tileEntity.TileEntityCage;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIGoToCage extends EntityAIBase {
	
    private EntityCreature temptedEntity;
    
    private double targetX;
    private double targetY;
    private double targetZ;
    
    private TileEntityCage tileEntity;
    
    private boolean isRunning;
    private Item item;
    private boolean avoidWater;

    public EntityAIGoToCage(EntityCreature entity, double p_i45316_2_, Item item) {
        this.temptedEntity = entity;
        this.item = item;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
 	
    	AxisAlignedBB aabb = this.temptedEntity.getBoundingBox();
    	aabb.expand(10, 10, 10);
    	for(double x = aabb.minX; x < aabb.maxX; x++) {
    		for(double y = aabb.minY; x < aabb.maxY; y++) {
    			for(double z = aabb.minZ; x < aabb.maxZ; z++) {
    				TileEntity tileEntity = this.temptedEntity.worldObj.getTileEntity((int)x, (int)y, (int)z);
    	    		if(tileEntity != null && tileEntity instanceof TileEntityCage) {
    	    			
    	    			TileEntityCage tileEntityCage = (TileEntityCage) tileEntity;
    	    			
    	    			if(tileEntityCage.getStackInSlot(0).getItem().equals(Items.gunpowder) || tileEntityCage.getStackInSlot(1).getItem().equals(Items.gunpowder) || tileEntityCage.getStackInSlot(2).getItem().equals(Items.gunpowder)) {
    	    			
	    	    			if(this.tileEntity != null) {
	    	    				int x1 = this.tileEntity.xCoord;
	    	    				int y1 = this.tileEntity.yCoord;
	    	    				int z1 = this.tileEntity.zCoord;
	    	    				
	    	    				int x2 = tileEntity.xCoord;
	    	    				int y2 = tileEntity.yCoord;
	    	    				int z2 = tileEntity.zCoord;
	    	    				
	    	    				AxisAlignedBB aabb1 = AxisAlignedBB.getBoundingBox(x1, y1, z1, x1, y1, z1);
	    	    				
	    	    				AxisAlignedBB aabb2 = AxisAlignedBB.getBoundingBox(x2, y2, z2, x2, y2, z2);
	    	    				
	    	    				double xDiff1 = Math.min(aabb.minX - aabb1.maxX, aabb.maxX - aabb1.minX);
	    	    				double yDiff1 = Math.min(aabb.minY - aabb1.maxY, aabb.maxY - aabb1.minY);
	    	    				double zDiff1 = Math.min(aabb.minZ - aabb1.maxZ, aabb.maxZ - aabb1.minZ);
	    	    				
	    	    				double xDiff2 = Math.min(aabb.minX - aabb2.maxX, aabb.maxX - aabb2.minX);
	    	    				double yDiff2 = Math.min(aabb.minY - aabb2.maxY, aabb.maxY - aabb2.minY);
	    	    				double zDiff2 = Math.min(aabb.minZ - aabb2.maxZ, aabb.maxZ - aabb2.minZ);
	    	    				
	    	    				int subResult = 0;
	    	    				
	    	    				if(xDiff1 < xDiff2) subResult++;
	    	    				if(yDiff1 < yDiff2) subResult++;
	    	    				if(zDiff1 < zDiff2) subResult++;
	    	    				
	    	    				if(subResult < 2) {
	    	    					this.tileEntity = (TileEntityCage)tileEntity;
	    	    					return true;
	    	    				} else {
	    	    					return false;
	    	    				}
	    	    				
	
	    	    			} else {
	    	    				this.tileEntity = (TileEntityCage)tileEntity;
	    	    				return true;
	    	    			}
    	    			}
    	    		}
    	    	}
        	}
    	}
		return false;
    }

    public boolean continueExecuting() { return this.shouldExecute(); }

    public void startExecuting() {
        this.isRunning = true;
        this.avoidWater = this.temptedEntity.getNavigator().getAvoidsWater();
        this.temptedEntity.getNavigator().setAvoidsWater(true);
    }

    public void resetTask() {
        this.tileEntity = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.isRunning = false;
        this.temptedEntity.getNavigator().setAvoidsWater(this.avoidWater);
    }

    public void updateTask() {
    	this.temptedEntity.getNavigator().tryMoveToXYZ(this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, 1.0);
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}