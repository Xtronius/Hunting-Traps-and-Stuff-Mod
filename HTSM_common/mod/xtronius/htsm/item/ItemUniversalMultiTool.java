package mod.xtronius.htsm.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUniversalMultiTool extends Item {
	
	public ItemUniversalMultiTool() {
		this.setTextureName("ItemUniversalMultiTool");
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
	}
	
//	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
//        
//		
//		return stack;
//    }
}
