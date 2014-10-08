package mod.xtronius.htsm.util;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.ItemUpgrade;
import mod.xtronius.htsm.tileEntity.ITileEntityUpgradable;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class Util {

	public static String getUnlocalizedNameInefficiently(Item item) {
        String s = item.getUnlocalizedName() + ".name";
        return s == null ? "" : StatCollector.translateToLocal(s);
    }
	
	public static String getUnwrappedUnlocalizedName(Item item) {
        return item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(".") + 1);
    }
	
	public static String getUnwrappedUnlocalizedName(String name) {
        return name.substring(name.indexOf(".") + 1);
    }
}
