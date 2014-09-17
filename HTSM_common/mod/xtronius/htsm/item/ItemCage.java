package mod.xtronius.htsm.item;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemCage extends Item {
	
	public ItemCage() {
		this.setTextureName("ItemUniversalMultiTool");
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		
//		world.setTileEntity(x, y+1, x, new TileEntityCage(stack));
		
		int xCoord = x;
		int yCoord = y;
		int zCoord = z;
		
		if (meta == 4) --xCoord;
        if (meta == 5) ++xCoord;
		if (meta == 0) --yCoord;
        if (meta == 1) ++yCoord;
        if (meta == 2) --zCoord;
        if (meta == 3) ++zCoord;
        
		world.setBlock(xCoord, yCoord, zCoord, HTSM.blockInit.getBlockByName("BlockCage"));

		TileEntityCage tileEntity = (TileEntityCage) world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileEntity != null) {
			NBTTagCompound nbt = stack.getTagCompound();
			
			if(!nbt.hasNoTags()) {
				
				NBTTagList list = nbt.getTagList("CageItems", 10);

		        for (int i = 0; i < list.tagCount(); ++i) {
		            NBTTagCompound compound1 = list.getCompoundTagAt(i);
		            int j = compound1.getByte("Slot") & 255;

		            if (j >= 0 && j < 3) {
		                tileEntity.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(compound1));
		            }
		        }
		        
		        NBTTagCompound compound2 = (NBTTagCompound) nbt.getTag("EntityData");
		        tileEntity.setEntityData(compound2);
		        
		        if(tileEntity.getEntityData() != null)
		        	tileEntity.setCageClosed(true);
			}
		}
		
		return false;
    }
}
