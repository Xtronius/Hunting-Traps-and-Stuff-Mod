package mod.xtronius.htsm.tileEntity.containers.slot;

import mod.xtronius.htsm.core.HTSM;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCage extends Slot{

	public SlotCage(IInventory inv, int slotIndex, int x, int y) {
		super(inv, slotIndex, x, y);
	}

	public boolean isItemValid(ItemStack stack) {
        return HTSM.cageList.isValidItemStack(stack);
    }
}
