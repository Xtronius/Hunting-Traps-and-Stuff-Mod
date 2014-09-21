package mod.xtronius.htsm.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemCage extends Item {
	
	public ItemCage() {
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		
//		world.setTileEntity(x, y+1, x, new TileEntityCage(stack));
		
		if(!world.isRemote) {
		
			int xCoord = x;
			int yCoord = y;
			int zCoord = z;
			
			int playerX = (int) (player.posX -(int)player.posX > 0.5 ? Math.ceil(player.posX) : Math.floor(player.posX));
			int playerZ = (int) (player.posZ -(int)player.posZ > 0.5 ? Math.ceil(player.posZ) : Math.floor(player.posZ));
			
			if (meta == 4) --xCoord;
	        if (meta == 5) ++xCoord;
			if (meta == 0) --yCoord;
	        if (meta == 1) ++yCoord;
	        if (meta == 2) --zCoord;
	        if (meta == 3) ++zCoord;
	        
	        
			if((xCoord == playerX && zCoord == playerZ) || !(world.getBlock(xCoord, yCoord, zCoord).equals(Blocks.air))) return false;
			else {
				world.setBlock(xCoord, yCoord, zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
				stack.stackSize--;
			}
			
			TileEntityCage tileEntity = (TileEntityCage) world.getTileEntity(xCoord, yCoord, zCoord);
			
			if(tileEntity != null) {
				NBTTagCompound nbt = stack.getTagCompound();
				
				if(nbt != null && !nbt.hasNoTags()) {
					
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
			        return true;
				}
			}
		}
		return true;
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);

		if(stack.getTagCompound() != null) {
			NBTTagCompound nbt1 = (NBTTagCompound) stack.getTagCompound().getTag("EntityData");
			NBTTagList nbt2 = (NBTTagList) stack.getTagCompound().getTagList("CageItems", 10);
			if(nbt1 != null) list.add(Util.UCCTS + 'a' + "Entity Captured: " + Util.splitCamelCase(nbt1.getString("id")));
			else list.add(1, "No Entity Stored");
			if(nbt2.tagCount() > 0) {
		        for (int i = 0; i < nbt2.tagCount(); ++i) {
		            NBTTagCompound compound1 = nbt2.getCompoundTagAt(i);
		            int j = compound1.getByte("Slot") & 255;
		            
		            if (j >= 0 && j < 3) 
		                list.add(Util.UCCTS + 'a' + "Slot " + j + ": " + Util.getUnlocalizedNameInefficiently(Item.getItemById(compound1.getShort("id"))) +  ' ' + compound1.getByte("Count"));
		        }
			} else 
				list.add("No Items Stores");
		}

    }
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.block;
    }
}
