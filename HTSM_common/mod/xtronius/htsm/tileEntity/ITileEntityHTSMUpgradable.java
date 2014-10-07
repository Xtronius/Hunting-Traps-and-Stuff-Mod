package mod.xtronius.htsm.tileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ITileEntityHTSMUpgradable {

	
	public int getSizeUpgradeInventory();

	
	public ItemStack getStackInUpgradeSlot(int p_70301_1_);

	
	public void setUpgradeInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_);

	
	public int getUpgradeInventoryStackLimit();

	
	public boolean isItemValidForUpgradeSlot(int slot, ItemStack stack);

}
