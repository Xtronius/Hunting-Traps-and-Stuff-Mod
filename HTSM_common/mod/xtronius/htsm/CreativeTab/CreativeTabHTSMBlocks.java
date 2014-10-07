package mod.xtronius.htsm.CreativeTab;

import java.util.List;

import mod.xtronius.htsm.core.HTSM;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
 
public class CreativeTabHTSMBlocks extends CreativeTabs {
 
    public CreativeTabHTSMBlocks(int id, String unlocalizedName) {
        super(id, unlocalizedName);
    }
 
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return HTSM.itemInit.getItemByName("ItemCage");
    }
    
    public boolean hasSearchBar() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List list) {}
}