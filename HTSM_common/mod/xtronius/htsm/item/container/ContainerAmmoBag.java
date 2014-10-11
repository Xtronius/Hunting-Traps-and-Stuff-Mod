package mod.xtronius.htsm.item.container;

import mod.xtronius.htsm.item.ItemAmmoBag;
import mod.xtronius.htsm.item.container.slot.SlotAmmoBag;
import mod.xtronius.htsm.item.container.slot.SlotPlayerAmmoBag;
import mod.xtronius.htsm.item.inventory.InventoryAmmoBag;
import mod.xtronius.htsm.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAmmoBag extends ContainerHTSM {

    private final EntityPlayer entityPlayer;
    private final InventoryAmmoBag inventoryAmmoBag;

    private int bagInventoryRows = 4;
    private int bagInventoryColumns = 12;

    public ContainerAmmoBag(EntityPlayer entityPlayer, InventoryAmmoBag inventoryAmmoBag) {
        this.entityPlayer = entityPlayer;
        this.inventoryAmmoBag = inventoryAmmoBag;

        for (int bagRowIndex = 0; bagRowIndex < bagInventoryRows; ++bagRowIndex) {
            for (int bagColumnIndex = 0; bagColumnIndex < bagInventoryColumns; ++bagColumnIndex) {
            	this.addSlotToContainer(new SlotAmmoBag(this, inventoryAmmoBag, entityPlayer, bagColumnIndex + bagRowIndex * bagInventoryColumns, 8 + bagColumnIndex * 18, 18 + bagRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
        	this.addSlotToContainer(new SlotPlayerAmmoBag(entityPlayer.inventory, actionBarSlotIndex, 35 + actionBarSlotIndex * 18, 162));
        }
        
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
            	this.addSlotToContainer(new Slot(entityPlayer.inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 35 + inventoryColumnIndex * 18, 104 + inventoryRowIndex * 18));
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);

        if (!entityPlayer.worldObj.isRemote) {
            InventoryPlayer invPlayer = entityPlayer.inventory;
            for (ItemStack itemStack : invPlayer.mainInventory) {
                if (itemStack != null) {
                    if (NBTHelper.hasTag(itemStack, "AmmoBagOpen")) {
                        NBTHelper.removeTag(itemStack, "AmmoBagOpen");
                    }
                }
            }

            saveInventory(entityPlayer);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            if (slotIndex < bagInventoryRows * bagInventoryColumns) {
                if (!this.mergeItemStack(itemStack, bagInventoryRows * bagInventoryColumns, inventorySlots.size(), false)) {
                    return null;
                }
            }
            else if (itemStack.getItem() instanceof ItemAmmoBag) {
                if (slotIndex < (bagInventoryRows * bagInventoryColumns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS)) {
                    if (!this.mergeItemStack(itemStack, (bagInventoryRows * bagInventoryColumns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), inventorySlots.size(), false)) {
                        return null;
                    }
                }
                else if (!this.mergeItemStack(itemStack, bagInventoryRows * bagInventoryColumns, (bagInventoryRows * bagInventoryColumns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemStack, 0, bagInventoryRows * bagInventoryColumns, false)) {
                return null;
            }


            if (itemStack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }

    public void saveInventory(EntityPlayer entityPlayer) {
        inventoryAmmoBag.onGuiSaved(entityPlayer);
    }
}
