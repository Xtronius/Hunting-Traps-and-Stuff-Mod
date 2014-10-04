package mod.xtronius.htsm.tileEntity;

import mod.xtronius.htsm.handlers.PacketHandler;
import mod.xtronius.htsm.packet.PacketSpikeData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySpike extends TickingTileEntity implements IInventory{
	
	private ForgeDirection orientation = ForgeDirection.UP;
	private ItemStack[] invContents = new ItemStack[1];
	
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
        
        NBTTagCompound nbtBlockData = (NBTTagCompound) compound.getTag("BlockData");
        
        if (nbtBlockData.hasKey("BlockOrientation")) {
            this.orientation = ForgeDirection.getOrientation(nbtBlockData.getByte("BlockOrientation"));
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
        
        NBTTagCompound nbtBlockData = new NBTTagCompound();
        
        nbtBlockData.setByte("BlockOrientation", (byte) orientation.ordinal());
        
        compound.setTag("BlockData", nbtBlockData);
    }
	
	 @Override
    public Packet getDescriptionPacket() {
		 return PacketHandler.INSTANCE.getPacketFrom(new PacketSpikeData((byte) this.getOrientation().ordinal(), this.xCoord, this.yCoord, this.zCoord));
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
	
	public ForgeDirection getOrientation() { return orientation; }
    public void setOrientation(ForgeDirection orientation) { this.orientation = orientation; }
    public void setOrientation(int orientation) { this.orientation = ForgeDirection.getOrientation(orientation); }
}
