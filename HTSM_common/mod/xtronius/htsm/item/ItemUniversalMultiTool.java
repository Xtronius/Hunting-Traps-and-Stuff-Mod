package mod.xtronius.htsm.item;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemUniversalMultiTool extends Item {
	
	public ItemUniversalMultiTool() {
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
	}
	

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			if(player.isSneaking()) {
				TileEntity tileEntity = world.getTileEntity(x, y, z);
				if(tileEntity instanceof TileEntityCage) {
					TileEntityCage tileEntityCage = (TileEntityCage) world.getTileEntity(x, y, z);	
					ItemStack stack = new ItemStack(HTSM.itemInit.getItemByName("ItemCage"));
					stack.setTagCompound(new NBTTagCompound());
					NBTTagCompound nbt = stack.stackTagCompound;
					NBTTagList list = new NBTTagList();
			        
			        for (int i = 0; i < 3; ++i) {
			            if (tileEntityCage.getStackInSlot(i) != null) {
			                NBTTagCompound compound1 = new NBTTagCompound();
			                compound1.setByte("Slot", (byte)i);
			                tileEntityCage.getStackInSlot(i).writeToNBT(compound1);
			                list.appendTag(compound1);
			            }
			        }
			        nbt.setTag("CageItems", list);
			        if(tileEntityCage.getEntityData() != null) 
				        nbt.setTag("EntityData", tileEntityCage.getEntityData());
			        
					//player.inventory.addItemStackToInventory(stack);
					EntityItem entityItem = new EntityItem(world, x, y, z, stack);
					
					entityItem.setVelocity(0.0d, 0.0d, 0.0d);
					
					if(player.inventory.getFirstEmptyStack() > -1)
						entityItem.setPosition(player.posX, player.posY, player.posZ);
					
					world.spawnEntityInWorld(entityItem);
					
					for(int i = 0; i < tileEntityCage.getSizeInventory(); i++)
						tileEntityCage.setInventorySlotContents(i, null);
					
					tileEntityCage.setEntityData(null);
					tileEntityCage.releaseEntity();
					
					world.setBlockToAir(x, y, z);
					
					world.playSoundAtEntity(player,(Reference.MOD_ASSET + ':' + "/sounds/" + "ItemUnviversalMultiTool"), 1.0f, 1.0f);
				}
			}
		}
		return false;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.block;
    }
}
