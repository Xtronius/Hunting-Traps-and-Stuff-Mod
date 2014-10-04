package mod.xtronius.htsm.tileEntity;

import java.util.ArrayList;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.handlers.PacketHandler;
import mod.xtronius.htsm.packet.PacketCageData;
import mod.xtronius.htsm.packet.PacketPlaqueData;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelPlaque;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityPlaque extends TickingTileEntity implements IInventory{
	
	private ItemStack[] invContents = new ItemStack[1];
	
	@Override
	public void updateEntity() {
		super.updateEntity();
	}
	
	@Override
	public boolean canUpdate() {
        return true;
    }
	
	@Override
	protected void intervalUpdate() {
		PacketHandler.INSTANCE.getPacketFrom(new PacketPlaqueData(this.getStackInSlot(0), this.xCoord, this.yCoord, this.zCoord));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);   
        
        NBTTagList list = compound.getTagList("Items", 10);
        this.invContents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound compound1 = list.getCompoundTagAt(i);
            int j = compound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.invContents.length)  this.invContents[j] = ItemStack.loadItemStackFromNBT(compound1);
        }
    }
	
	@Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        
        for (int i = 0; i < this.invContents.length; ++i) {
            if (this.invContents[i] != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Slot", (byte)i);
                this.invContents[i].writeToNBT(compound1);
                list.appendTag(compound1);
            }
        }
        
        compound.setTag("Items", list);
    }
	
	@Override
	public int getSizeInventory() { return 1; }
	@Override
	public ItemStack getStackInSlot(int slot) { return this.invContents[slot]; }

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
