package mod.xtronius.htsm.tileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ITileEntityHTSMUpgradable {
	
	public int getSizeUpgradeInventory();

	
	public ItemStack getStackInUpgradeSlot(int slot);

	
	public void setUpgradeInventorySlotContents(int slot, ItemStack stack);

	
	public int getUpgradeInventoryStackLimit();

	
	public boolean isItemValidForUpgradeSlot(int slot, ItemStack stack);
	
	public boolean alreadyContainesUpgrade(ItemStack stack);

}
