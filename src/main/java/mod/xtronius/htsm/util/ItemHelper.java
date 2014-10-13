package mod.xtronius.htsm.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import java.util.Comparator;
import java.util.UUID;

public class ItemHelper {
    public static ItemStack cloneItemStack(ItemStack itemStack, int stackSize) {
        ItemStack clonedItemStack = itemStack.copy();
        clonedItemStack.stackSize = stackSize;
        return clonedItemStack;
    }

    public static Comparator<ItemStack> comparator = new Comparator<ItemStack>()  {
        public int compare(ItemStack itemStack1, ItemStack itemStack2)  {
            if (itemStack1 != null && itemStack2 != null) {
                if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0) {
                    if (itemStack1.getItem() == itemStack2.getItem()) {
                        if (itemStack1.getItemDamage() == itemStack2.getItemDamage()) {
                            if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound()) {
                                if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))  {
                                    return (itemStack1.stackSize - itemStack2.stackSize);
                                }
                                else {
                                    return (itemStack1.getTagCompound().hashCode() - itemStack2.getTagCompound().hashCode());
                                }
                            }
                            else if (!(itemStack1.hasTagCompound()) && itemStack2.hasTagCompound()) {
                                return -1;
                            }
                            else if (itemStack1.hasTagCompound() && !(itemStack2.hasTagCompound())) {
                                return 1;
                            }
                            else {
                                return (itemStack1.stackSize - itemStack2.stackSize);
                            }
                        }
                        else {
                            return (itemStack1.getItemDamage() - itemStack2.getItemDamage());
                        }
                    }
                    else {
                        return itemStack1.getItem().getUnlocalizedName(itemStack1).compareToIgnoreCase(itemStack2.getItem().getUnlocalizedName(itemStack2));
                    }
                }
                else {
                    return Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem());
                }
            }
            else if (itemStack1 != null) {
                return -1;
            }
            else if (itemStack2 != null) {
                return 1;
            }
            else {
                return 0;
            }
        }
    };

    /**
     * Compares two ItemStacks for equality, testing itemID, metaData, stackSize, and their NBTTagCompounds (if they are
     * present)
     *
     * @param first  The first ItemStack being tested for equality
     * @param second The second ItemStack being tested for equality
     * @return true if the two ItemStacks are equivalent, false otherwise
     */
    public static boolean equals(ItemStack first, ItemStack second) {
        return (comparator.compare(first, second) == 0);
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 != null && itemStack2 != null) {
            if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0) {
                if (itemStack1.getItem() == itemStack2.getItem()) {
                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage()) {
                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound()) {
                            if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2)) {
                                return true;
                            }
                        }
                        else {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static int compare(ItemStack itemStack1, ItemStack itemStack2) {
        return comparator.compare(itemStack1, itemStack2);
    }

    public static String toString(ItemStack itemStack) {
        if (itemStack != null) {
            return String.format("%sxitemStack[%s@%s]", itemStack.stackSize, itemStack.getUnlocalizedName(), itemStack.getItemDamage());
        }

        return "null";
    }
    
    public static ItemStack[] loadItemsToArray(NBTTagCompound nbt, ItemStack[] array) {
	    if (nbt != null && nbt.hasKey("Items")) {
	        if (nbt.hasKey("Items")) {
	            NBTTagList tagList = nbt.getTagList("Items", 10);
	            for (int i = 0; i < tagList.tagCount(); ++i) {
	                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
	                byte slotIndex = tagCompound.getByte("Slot");
	                if (slotIndex >= 0 && slotIndex < array.length) {
	                    array[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
	                }
	            }
	            return array;
	        }
	    }
	    return null;
    }
    
    public static NBTTagCompound saveItemsToNBT(NBTTagCompound nbt, ItemStack[] array) {
    	
    	NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < array.length; ++currentIndex) {
            if (array[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                array[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbt.setTag("Items", tagList);
        
        return nbt;
    }
}
