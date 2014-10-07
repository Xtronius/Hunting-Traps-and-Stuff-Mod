package mod.xtronius.htsm.tileEntity;

import java.util.ArrayList;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.handlers.PacketHandler;
import mod.xtronius.htsm.packet.PacketSpikeData;
import mod.xtronius.htsm.util.UpgradeHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySpike extends TickingTileEntity implements IInventory, ITileEntityHTSMUpgradable{
	
	private ForgeDirection orientation = ForgeDirection.UP;
	private ItemStack[] invContents = new ItemStack[1];
	private ItemStack[] upgradeInvContents = new ItemStack[1];
	
	protected void intervalUpdate() {
		
		if(!this.worldObj.isRemote) {
			
			HTSM.ch.INSTANCE.sendToAll(new PacketSpikeData((byte) this.getOrientation().ordinal(), this.xCoord, this.yCoord, this.zCoord));
			
			for(int upgradeSlot = 0; upgradeSlot < upgradeInvContents.length; upgradeSlot++) {
				if(upgradeInvContents[upgradeSlot] != null) {
					switch(upgradeInvContents[upgradeSlot].getItemDamage()) {
						case UpgradeHelper.UPGRADE_HOPPER: updateHopper();
					}
				}
			}
		}
	}
	
	private void updateHopper() {
		IInventory inventory = null;
		
		ArrayList<TileEntity> tileEntityList = new ArrayList<TileEntity>();
		tileEntityList.add(0, this.worldObj.getTileEntity(this.xCoord, this.yCoord+1, this.zCoord));
		tileEntityList.add(1, this.worldObj.getTileEntity(this.xCoord, this.yCoord-1, this.zCoord));
		tileEntityList.add(2, this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord-1));
		tileEntityList.add(3, this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord+1));
		tileEntityList.add(4, this.worldObj.getTileEntity(this.xCoord+1, this.yCoord, this.zCoord));
		tileEntityList.add(5, this.worldObj.getTileEntity(this.xCoord-1, this.yCoord, this.zCoord));
		
		ForgeDirection UP = ForgeDirection.UP;
		ForgeDirection DOWN = ForgeDirection.DOWN;
		ForgeDirection NORTH = ForgeDirection.NORTH;
		ForgeDirection SOUTH = ForgeDirection.SOUTH;
		ForgeDirection EAST = ForgeDirection.EAST;
		ForgeDirection WEST = ForgeDirection.WEST;
		
		if(this.getOrientation() == UP)
			tileEntityList.remove(tileEntityList.get(1));
		else if(this.getOrientation() == DOWN)
			tileEntityList.remove(tileEntityList.get(0));
		else if(this.getOrientation() == NORTH)
			tileEntityList.remove(tileEntityList.get(2));
		else if(this.getOrientation() == SOUTH)
			tileEntityList.remove(tileEntityList.get(3));
		else if(this.getOrientation() == EAST)
			tileEntityList.remove(tileEntityList.get(4));
		else if(this.getOrientation() == WEST)
			tileEntityList.remove(tileEntityList.get(5));
		
		for(TileEntity tileEntity : tileEntityList) {
			if((tileEntity != null) && (tileEntity instanceof IInventory || tileEntity instanceof ISidedInventory )) {
				inventory = (IInventory) tileEntity;
			}
		}
		
		if(inventory != null) {
			Block block = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
			//Gets the bounding box of the block obtained above, then it offsets it and expands it
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
			AxisAlignedBB aabb1 = aabb.getOffsetBoundingBox(this.xCoord, this.yCoord, this.zCoord);
			AxisAlignedBB aabb2 = null;
			
			if(this.getOrientation() == UP)
				aabb2 = AxisAlignedBB.getBoundingBox(aabb1.minX, aabb1.minY, aabb1.minZ, aabb1.maxX, aabb1.maxY, aabb1.maxZ);
			else if(this.getOrientation() == DOWN)
				aabb2 = AxisAlignedBB.getBoundingBox(aabb1.minX, aabb1.minY, aabb1.minZ, aabb1.maxX, aabb1.maxY, aabb1.maxZ);
			else if(this.getOrientation() == NORTH)
				aabb2 = AxisAlignedBB.getBoundingBox(aabb1.minX, aabb1.minY, aabb1.minZ, aabb1.maxX, aabb1.maxY, aabb1.maxZ);
			else if(this.getOrientation() == SOUTH)
				aabb2 = AxisAlignedBB.getBoundingBox(aabb1.minX, aabb1.minY, aabb1.minZ, aabb1.maxX, aabb1.maxY, aabb1.maxZ);
			else if(this.getOrientation() == EAST)
				aabb2 = AxisAlignedBB.getBoundingBox(aabb1.minX, aabb1.minY, aabb1.minZ, aabb1.maxX, aabb1.maxY, aabb1.maxZ);
			else if(this.getOrientation() == WEST)
				aabb2 = AxisAlignedBB.getBoundingBox(aabb1.minX, aabb1.minY, aabb1.minZ, aabb1.maxX, aabb1.maxY, aabb1.maxZ);
			else aabb2 = AxisAlignedBB.getBoundingBox(aabb1.minX, aabb1.minY, aabb1.minZ, aabb1.maxX, aabb1.maxY, aabb1.maxZ);
			
			ArrayList<EntityItem> entityList = (ArrayList<EntityItem>) this.worldObj.getEntitiesWithinAABB(EntityItem.class, aabb2);
			for(EntityItem entity : entityList) {
				if(entity != null) {
					ItemStack stack = entity.getEntityItem().copy();
					
					for(int slot = 0; slot < inventory.getSizeInventory(); slot++) {
						if(inventory.getStackInSlot(slot) == null) {
							inventory.setInventorySlotContents(slot, stack);
							stack = null;
							entity.setDead();
							break;
						} else if(inventory.getStackInSlot(slot).stackSize < inventory.getStackInSlot(slot).getMaxStackSize()){
							ItemStack stack2 = inventory.getStackInSlot(slot).copy();
							if(stack.getItem().equals(stack2.getItem())) {
								if(stack.stackSize + stack2.stackSize > stack.getMaxStackSize()) {
									int diff = (stack.stackSize + stack2.stackSize)%stack.getMaxStackSize();
									
									ItemStack stack3 = stack.copy();
									ItemStack stack4 = stack.copy();
									stack3.stackSize = stack.getMaxStackSize();
									stack4.stackSize  = diff;
									
									inventory.setInventorySlotContents(slot, stack3);
									entity.setEntityItemStack(stack4);
									
									break;
								} else {
									ItemStack stack3 = stack.copy();
									stack3.stackSize = stack.stackSize + stack2.stackSize;
									
									inventory.setInventorySlotContents(slot, stack3);
									stack = null;
									entity.setDead();
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);   
        
        NBTTagList invList = compound.getTagList("Items", 10);
        this.invContents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < invList.tagCount(); ++i) {
            NBTTagCompound compound1 = invList.getCompoundTagAt(i);
            int j = compound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.invContents.length)  this.invContents[j] = ItemStack.loadItemStackFromNBT(compound1);
        }
        
        NBTTagList upgradeInvList = compound.getTagList("UpgradeItems", 10);
        this.invContents = new ItemStack[this.getSizeUpgradeInventory()];

        for (int i = 0; i < upgradeInvList.tagCount(); ++i) {
            NBTTagCompound compound1 = upgradeInvList.getCompoundTagAt(i);
            int j = compound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.upgradeInvContents.length)  this.upgradeInvContents[j] = ItemStack.loadItemStackFromNBT(compound1);
        }
        
        NBTTagCompound nbtBlockData = (NBTTagCompound) compound.getTag("BlockData");
        
        if (nbtBlockData.hasKey("BlockOrientation")) {
            this.orientation = ForgeDirection.getOrientation(nbtBlockData.getByte("BlockOrientation"));
        }
    }

	@Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList invList = new NBTTagList();
        
        for (int i = 0; i < this.invContents.length; ++i) {
            if (this.invContents[i] != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Slot", (byte)i);
                this.invContents[i].writeToNBT(compound1);
                invList.appendTag(compound1);
            }
        }
        
        compound.setTag("Items", invList);
        
        NBTTagList upgradeInvList = new NBTTagList();
        
        for (int i = 0; i < this.upgradeInvContents.length; ++i) {
            if (this.upgradeInvContents[i] != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Slot", (byte)i);
                this.upgradeInvContents[i].writeToNBT(compound1);
                invList.appendTag(compound1);
            }
        }
        
        compound.setTag("UpgradeItems", invList);
        
        NBTTagCompound nbtBlockData = new NBTTagCompound();
        
        nbtBlockData.setByte("BlockOrientation", (byte) orientation.ordinal());
        
        compound.setTag("BlockData", nbtBlockData);
    }
	
	 @Override
    public Packet getDescriptionPacket() {
		 return PacketHandler.INSTANCE.getPacketFrom(new PacketSpikeData((byte) this.getOrientation().ordinal(), this.xCoord, this.yCoord, this.zCoord));
    }
	
	@Override
	public int getSizeInventory() { return this.invContents.length; }

	@Override
	public ItemStack getStackInSlot(int slot) { return this.invContents[slot]; }
	
	public int getSizeUpgradeInventory() { return this.upgradeInvContents.length; }

	public ItemStack getStackInUpgradeSlot(int slot) { return this.upgradeInvContents[slot]; }

	@Override
	public ItemStack decrStackSize(int slotIndex, int i) {
		 if (this.invContents[slotIndex] != null) {
	            ItemStack itemstack;

	            if (this.invContents[slotIndex].stackSize <= i) {
	                itemstack = this.invContents[slotIndex];
	                this.invContents[slotIndex] = null;
	                this.markDirty();
	                return itemstack;
	            }
	            else {
	                itemstack = this.invContents[slotIndex].splitStack(i);
	                
	                if (this.invContents[slotIndex].stackSize == 0) 
	                    this.invContents[slotIndex] = null;
	                
	                this.markDirty();
	                return itemstack;
	            }
	        }
	        else return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) { return this.invContents[slot]; }

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) { this.invContents[slot] = stack; }
	
	public void setUpgradeInventorySlotContents(int slot, ItemStack stack) { this.upgradeInvContents[slot] = stack; }

	@Override
	public String getInventoryName() { return "Spike"; }

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
	
	@Override
	public int getUpgradeInventoryStackLimit() { return 64; }

	@Override
	public boolean isItemValidForUpgradeSlot(int slot, ItemStack stack) { return  (stack != null && stack.getItem().equals(HTSM.itemInit.getItemByName("ItemUpgrade")) && stack.getItemDamage() != 0); }
	
	public boolean canUpdate()  {return true; }
	
	public ForgeDirection getOrientation() { return orientation; }
    public void setOrientation(ForgeDirection orientation) { this.orientation = orientation; }
    public void setOrientation(int orientation) { this.orientation = ForgeDirection.getOrientation(orientation); }

	@Override
	public boolean alreadyContainesUpgrade(ItemStack stack) {
		for(int i = 0; i < this.upgradeInvContents.length; i++) {
			if((this.getStackInSlot(i) != null) && (this.getStackInSlot(i).equals(stack))) return true;
		}
		return false;
	}
}
