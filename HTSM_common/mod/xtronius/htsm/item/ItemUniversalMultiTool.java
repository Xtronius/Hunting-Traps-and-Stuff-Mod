package mod.xtronius.htsm.item;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.packet.PacketSpikeData;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
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
import net.minecraftforge.common.util.ForgeDirection;

public class ItemUniversalMultiTool extends Item {
	
	public ItemUniversalMultiTool() {
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
	}
	

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			if(player.isSneaking()) {
				TileEntity tileEntity = world.getTileEntity(x, y, z);
				if(tileEntity != null && tileEntity instanceof TileEntityCage) { 
					handleTileEntityCage(itemStack, world, player, x, y, z, meta, hitX, hitY, hitZ);
					HTSM.proxy.playSound("ItemUniversalMultiTool", x, y, z, 1.0f, 1.0f);
				} else if(tileEntity != null && tileEntity instanceof TileEntitySpike) {
					handleRemoveSpikeUpgrade(itemStack, world, player, x, y, z, meta, hitX, hitY, hitZ);
					HTSM.proxy.playSound("ItemUniversalMultiTool", x, y, z, 1.0f, 1.0f);
				}
			} else {
				TileEntity tileEntity = world.getTileEntity(x, y, z);
				if(tileEntity != null && tileEntity instanceof TileEntitySpike) {
					handleRotateSpike(itemStack, world, player, x, y, z, meta, hitX, hitY, hitZ);
					HTSM.proxy.playSound("ItemUniversalMultiTool", x, y, z, 1.0f, 1.0f);
				}
			}
		}
		return false;
	}
	private void handleRemoveSpikeUpgrade(ItemStack itemStack, World world, EntityPlayer player, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		TileEntitySpike tileEntitySpike = (TileEntitySpike) world.getTileEntity(x, y, z);
		
		for(int i = 0; i < tileEntitySpike.getSizeInventory(); i++) {
			ItemStack stack = tileEntitySpike.getStackInSlot(i);
			
			if(stack != null) {
				EntityItem entity = new EntityItem(world, x, y, z, stack);
				tileEntitySpike.setInventorySlotContents(i, null);
				world.spawnEntityInWorld(entity);
			}
		}
	}
	
	private void handleRotateSpike(ItemStack itemStack, World world, EntityPlayer player, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		TileEntitySpike tileEntitySpike = (TileEntitySpike) world.getTileEntity(x, y, z);
		
		ForgeDirection UP = ForgeDirection.UP;
		ForgeDirection DOWN = ForgeDirection.DOWN;
		ForgeDirection NORTH = ForgeDirection.NORTH;
		ForgeDirection SOUTH = ForgeDirection.SOUTH;
		ForgeDirection EAST = ForgeDirection.EAST;
		ForgeDirection WEST = ForgeDirection.WEST;
		
		if(tileEntitySpike.getOrientation() == UP)
			tileEntitySpike.setOrientation(ForgeDirection.DOWN);
		else if(tileEntitySpike.getOrientation() == DOWN)
			tileEntitySpike.setOrientation(ForgeDirection.NORTH);
		else if(tileEntitySpike.getOrientation() == NORTH)
			tileEntitySpike.setOrientation(ForgeDirection.SOUTH);
		else if(tileEntitySpike.getOrientation() == SOUTH)
			tileEntitySpike.setOrientation(ForgeDirection.EAST);
		else if(tileEntitySpike.getOrientation() == EAST)
			tileEntitySpike.setOrientation(ForgeDirection.WEST);
		else if(tileEntitySpike.getOrientation() == WEST)
			tileEntitySpike.setOrientation(ForgeDirection.UP);
		
		HTSM.ch.INSTANCE.sendToAll(new PacketSpikeData((byte) tileEntitySpike.getOrientation().ordinal(), tileEntitySpike.xCoord, tileEntitySpike.yCoord, tileEntitySpike.zCoord));
	}
	
	private void handleTileEntityCage(ItemStack itemStack, World world, EntityPlayer player, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
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
        
        NBTTagCompound miscTag = new NBTTagCompound();
        
        miscTag.setBoolean("isCageClosed", tileEntityCage.isCageClosed());
        
        nbt.setTag("Misc", miscTag);
        
        if(tileEntityCage.getEntityData() != null) 
	        nbt.setTag("EntityData", tileEntityCage.getEntityData());
        
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
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.block;
    }
}
