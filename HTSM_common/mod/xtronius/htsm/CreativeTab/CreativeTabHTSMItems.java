package mod.xtronius.htsm.CreativeTab;

import java.util.List;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.ItemUpgrade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
 
public class CreativeTabHTSMItems extends CreativeTabs {
 
    public CreativeTabHTSMItems(int id, String unlocalizedName) {
 
        super(id, unlocalizedName);
    }
 
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return HTSM.itemInit.getItemByName("ItemUniversalMultiTool");
    }
    
    public boolean hasSearchBar() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List itemList) {
    	super.displayAllReleventItems(itemList);
        addMetaDataItems(itemList, HTSM.itemInit.getItemByName("ItemUpgrade"), ItemUpgrade.names.length);
    }
    
    public void addMetaDataItems(List list, Item item, int range) {
         
        for (int i = 0; i < range; ++i) {
        	list.add(new ItemStack(item, 1, i));
        }
    }
    
}