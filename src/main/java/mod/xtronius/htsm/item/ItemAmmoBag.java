package mod.xtronius.htsm.item;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.gui.GuiIDs;
import mod.xtronius.htsm.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmmoBag extends Item {

	@Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (!world.isRemote) {
        	
        	 NBTHelper.setUUID(itemStack);
             NBTHelper.setBoolean(itemStack, "AmmoBagOpen", true);
        	
            entityPlayer.openGui(HTSM.INSTANCE, GuiIDs.guiAmmoBag, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
        }

        return itemStack;
    }
}
