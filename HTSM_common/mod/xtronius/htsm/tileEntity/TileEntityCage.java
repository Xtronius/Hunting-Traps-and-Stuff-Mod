package mod.xtronius.htsm.tileEntity;

import java.util.List;

import mod.xtronius.htsm.entity.ai.EntityAIGoToCage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCage extends TileEntity implements IInventory{
	
	private ItemStack[] cageContents = new ItemStack[3];

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			for(int i = 0; i < 3; i++) {
				if(this.getStackInSlot(i) != null) {
	
					List<Entity> list = this.worldObj.getLoadedEntityList();
					
					for(Entity entity : list) { 
						if(entity instanceof EntityPig) {
//							((EntityPig)entity).tasks.addTask(4, new EntityAITempt((EntityPig)entity, 1.2D, Items.gunpowder, false));
							((EntityPig)entity).tasks.addTask(4, new EntityAIGoToCage((EntityPig)entity, 0, Items.gunpowder));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);   
    }

	@Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
    }

	@Override
	public int getSizeInventory() { return 3; }

	@Override
	public ItemStack getStackInSlot(int slot) { return this.cageContents[slot]; }

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		 if (this.cageContents[p_70298_1_] != null)
	        {
	            ItemStack itemstack;

	            if (this.cageContents[p_70298_1_].stackSize <= p_70298_2_)
	            {
	                itemstack = this.cageContents[p_70298_1_];
	                this.cageContents[p_70298_1_] = null;
	                this.markDirty();
	                return itemstack;
	            }
	            else
	            {
	                itemstack = this.cageContents[p_70298_1_].splitStack(p_70298_2_);

	                if (this.cageContents[p_70298_1_].stackSize == 0)
	                {
	                    this.cageContents[p_70298_1_] = null;
	                }

	                this.markDirty();
	                return itemstack;
	            }
	        }
	        else
	        {
	            return null;
	        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) { return this.cageContents[slot]; }

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) { this.cageContents[slot] = stack; }

	@Override
	public String getInventoryName() { return "Cage"; }

	@Override
	public boolean hasCustomInventoryName() { return false; }

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) { return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }
}
