package mod.xtronius.htsm.item.container.slot;

import mod.xtronius.htsm.core.HTSM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotPlayerAmmoBag extends Slot {

	public SlotPlayerAmmoBag(IInventory inventory, int slotIndex, int x, int y) {
		super(inventory, slotIndex, x, y);
	}

	public boolean canTakeStack(EntityPlayer player) {
		int slotMod = this.slotNumber-48;
		if(slotMod <= player.inventory.getSizeInventory()) {
			if(this.inventory.getStackInSlot(slotMod) != null) {
				if(this.inventory.getStackInSlot(slotMod).getItem().equals(HTSM.htsmItem.getItemByName("ItemAmmoBag")))
					return false;
			}
		}
		return true;
    }
}
