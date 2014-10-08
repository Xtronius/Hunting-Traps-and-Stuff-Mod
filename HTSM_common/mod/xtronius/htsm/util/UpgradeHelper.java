package mod.xtronius.htsm.util;

import mod.xtronius.htsm.item.ItemUpgrade;
import mod.xtronius.htsm.tileEntity.ITileEntityUpgradable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UpgradeHelper {
	
	public static final int UPGRADE_BLANK = 0;
	public static final int UPGRADE_HOPPER = 1;
	public static final int UPGRADE_REDSTONE = 2;
	public static final int UPGRADE_FIRE = 3;
	public static final int UPGRADE_POISON = 4;
	public static final int UPGRADE_WITHER = 5;
	public static final int UPGRADE_HUNGER = 6;
	public static final int UPGRADE_SLOWNESS = 7;
	public static final int UPGRADE_BLINDNESS = 8;
	public static final int UPGRADE_NAUSEA = 9;
	public static final int UPGRADE_OTHER = 10;

	public static void tryAddUpgradeToBlock(World world, EntityPlayer player, int x, int y, int z) {
		
		ITileEntityUpgradable tileEntity = (ITileEntityUpgradable) world.getTileEntity(x, y, z);
		
		if(tileEntity != null) {
			ItemStack stack = player.getCurrentEquippedItem();
			for(int i = 0; i < tileEntity.getSizeUpgradeInventory(); i++) {
				if(tileEntity.isItemValidForUpgradeSlot(i, stack)) {
					if(!tileEntity.alreadyContainesUpgrade(stack)) {
						if(tileEntity.getStackInUpgradeSlot(i) == null) {
							tileEntity.setUpgradeInventorySlotContents(i, stack);
							player.setCurrentItemOrArmor(0, null);
							Util.sendPlayerMessage(player, ColorHelper.GREEN + "Upgrade Inserted: " + ItemUpgrade.names[stack.getItemDamage()]);
							return;
						}
					} else {
						Util.sendPlayerMessage(player, ColorHelper.DARK_RED + "Upgrade Already Installed.");
						return;
					}
				} else return;
			}
		} else return;
		Util.sendPlayerMessage(player, ColorHelper.DARK_RED + "There are no more upgrade slots available.");
	}
}
