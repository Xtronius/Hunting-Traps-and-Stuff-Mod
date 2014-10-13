package mod.xtronius.htsm.item.container.slot;

import mod.xtronius.htsm.item.ItemAmmoBag;
import mod.xtronius.htsm.item.container.ContainerAmmoBag;
import mod.xtronius.htsm.item.gun.ammo.ItemAmmo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAmmoBag extends Slot {
    private final EntityPlayer entityPlayer;
    private ContainerAmmoBag containerAmmoBag;

    public SlotAmmoBag(ContainerAmmoBag containerAmmoBag, IInventory inventory, EntityPlayer player, int slotIndex, int x, int y) {
        super(inventory, slotIndex, x, y);
        this.entityPlayer = player;
        this.containerAmmoBag = containerAmmoBag;
    }

    @Override
    public void onSlotChange(ItemStack itemStack1, ItemStack itemStack2) {
        super.onSlotChange(itemStack1, itemStack2);
        containerAmmoBag.saveInventory(entityPlayer);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return !(itemStack.getItem() instanceof ItemAmmoBag) && (itemStack.getItem() instanceof ItemAmmo);
    }
}
