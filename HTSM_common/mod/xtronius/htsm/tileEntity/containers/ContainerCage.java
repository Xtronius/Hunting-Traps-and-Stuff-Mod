package mod.xtronius.htsm.tileEntity.containers;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.containers.slot.SlotCage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerCage extends Container{
	
	public TileEntityCage tileEntity;
		
	public ContainerCage(TileEntityCage tileEntity, InventoryPlayer playerInv, World world, int x, int y, int z) {
		
		this.tileEntity = tileEntity;
		for(int i = 0; i < 3; i++) 
			this.addSlotToContainer(new SlotCage(tileEntity, i, 62 + i * 18, 63));
    
		for (int j = 0; j < 3; ++j) 
			for (int k = 0; k < 9; ++k) 
				this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 140 + j * 18));
   
		for (int j = 0; j < 9; ++j) 
			this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 198));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) { return true; }
	
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
        	if(HTSM.cageList.isValidItemStack(slot.getStack())) {
        		ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();
	            if (slotIndex < this.getInv().getSizeInventory()) {
	                if (!this.mergeItemStack(itemstack1, this.getInv().getSizeInventory(), this.inventorySlots.size(), true)) {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 0, this.getInv().getSizeInventory(), false)) {
	                return null;
	            }
	
	            if (itemstack1.stackSize == 0) {
	                slot.putStack((ItemStack)null);
	            }
	            else {
	                slot.onSlotChanged();
	            }
        	}
        }
        return itemstack;
    }
	
	public IInventory getInv() { return this.tileEntity; }

}
