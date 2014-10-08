package mod.xtronius.htsm.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.util.ColorHelper;
import mod.xtronius.htsm.util.StringHelper;
import mod.xtronius.htsm.util.Util;
import net.minecraft.block.Block;
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
		this.setCreativeTab(HTSM.tabBlocks);
		this.setMaxStackSize(1);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);

        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
            meta = 1;
        }
        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z)) {
            if (meta == 0) --y;
            if (meta == 1) ++y;
            if (meta == 2) --z;
            if (meta == 3) ++z;
            if (meta == 4) --x;      
            if (meta == 5) ++x;
            
        }

        if (stack.stackSize == 0) {
            return false;
        }
        else if (!player.canPlayerEdit(x, y, z, meta, stack))  {
            return false;
        }
        else if (y == 255 && HTSM.blockInit.getBlockByName("BlockCage").getMaterial().isSolid()) {
            return false;
        }
        else if (world.canPlaceEntityOnSide(HTSM.blockInit.getBlockByName("BlockCage"), x, y, z, false, meta, player, stack)) {
            int i1 = this.getMetadata(stack.getItemDamage());
            int j1 = HTSM.blockInit.getBlockByName("BlockCage").onBlockPlaced(world, x, y, z, meta, hitX, hitY, hitZ, i1);

            if (placeBlockAt(stack, player, world, x, y, z, meta, hitX, hitY, hitZ, j1)) {
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), HTSM.blockInit.getBlockByName("BlockCage").stepSound.func_150496_b(), (HTSM.blockInit.getBlockByName("BlockCage").stepSound.getVolume() + 1.0F) / 2.0F, HTSM.blockInit.getBlockByName("BlockCage").stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        }
        else {
            return false;
        }
    }
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {

       if (!world.setBlock(x, y, z, HTSM.blockInit.getBlockByName("BlockCage"), metadata, 3)) {
           return false;
       }

       if (world.getBlock(x, y, z) == HTSM.blockInit.getBlockByName("BlockCage")) {
    	   HTSM.blockInit.getBlockByName("BlockCage").onBlockPlacedBy(world, x, y, z, player, stack);
    	   HTSM.blockInit.getBlockByName("BlockCage").onPostBlockPlaced(world, x, y, z, metadata);
    	   
    	   TileEntityCage tileEntity = (TileEntityCage) world.getTileEntity(x, y, z);
			
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
			        
			        NBTTagCompound compound3 = (NBTTagCompound) nbt.getTag("Misc");
			        
			        tileEntity.setCageClosed(compound3.getBoolean("isCageClosed"));
			        
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
	
	/* TileEntityCage tileEntity = (TileEntityCage) world.getTileEntity(xCoord, yCoord, zCoord);
			
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
			        
			        NBTTagCompound compound3 = (NBTTagCompound) nbt.getTag("Misc");
			        
			        tileEntity.setCageClosed(compound3.getBoolean("isCageClosed"));
			        
			        NBTTagCompound compound2 = (NBTTagCompound) nbt.getTag("EntityData");
			        tileEntity.setEntityData(compound2);
			        
			        if(tileEntity.getEntityData() != null)
			        	tileEntity.setCageClosed(true);
			        return true;
				}
			}
	*/
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);

		if(stack.getTagCompound() != null) {
			NBTTagCompound nbt1 = (NBTTagCompound) stack.getTagCompound().getTag("EntityData");
			NBTTagList nbt2 = (NBTTagList) stack.getTagCompound().getTagList("CageItems", 10);
			if(nbt1 != null) list.add(ColorHelper.GREEN + "Entity Captured: " + StringHelper.splitCamelCase(nbt1.getString("id")));
			else list.add(1, "No Entity Stored");
			if(nbt2.tagCount() > 0) {
		        for (int i = 0; i < nbt2.tagCount(); ++i) {
		            NBTTagCompound compound1 = nbt2.getCompoundTagAt(i);
		            int j = compound1.getByte("Slot") & 255;
		            
		            if (j >= 0 && j < 3) 
		                list.add(ColorHelper.GREEN + "Slot " + j + ": " + Util.getUnlocalizedNameInefficiently(Item.getItemById(compound1.getShort("id"))) +  ' ' + compound1.getByte("Count"));
		        }
			} else 
				list.add("No Items Stores");
		}

    }
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.block;
    }
}
