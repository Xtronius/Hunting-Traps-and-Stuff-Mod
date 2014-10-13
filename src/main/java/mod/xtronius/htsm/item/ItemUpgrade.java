package mod.xtronius.htsm.item;

import java.util.List;

import javax.swing.Icon;

import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUpgrade extends Item{
	
	public ItemUpgrade() {
       this.setMaxStackSize(1);
       this.setHasSubtypes(true);
    }
	
	public static final String[] names = new String[] {"Blank", "Hopper", "Redstone", "Fire", "Poison", "Wither", "Hunger", "Slowness", "Blindness", "Nausea", "Other"};
    
	public String getUnlocalizedName(ItemStack par1ItemStack) {
	    int i = par1ItemStack.getItemDamage();
	    return super.getUnlocalizedName() + "." + names[i] + "Upgrade";
	}
   
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public IIcon getIconFromDamage(int par1) {
		return icons[par1];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs creativeTab, List list) {
	    for (int x = 0; x < names.length; x++) {
	        list.add(new ItemStack(this, 1, x));
	    }
	}
	      
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
       icons = new IIcon[names.length];
             
       for(int i = 0; i < icons.length; i++) {
    	   icons[i] = reg.registerIcon(Reference.MOD_ASSET + ":" + names[i] + "Upgrade");
       }
	}
}
