package mod.xtronius.htsm.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.packet.PacketSpikeData;
import mod.xtronius.htsm.tileEntity.ITileEntityRemovable;
import mod.xtronius.htsm.tileEntity.ITileEntityRotatable;
import mod.xtronius.htsm.tileEntity.ITileEntityUpgradable;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import mod.xtronius.htsm.util.ColorHelper;
import mod.xtronius.htsm.util.StringHelper;
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
		this.setMaxStackSize(1);
		this.hasSubtypes = true;
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack) {
	    int i = par1ItemStack.getItemDamage();
	    return super.getUnlocalizedName() + "." + StringHelper.removeSpaces(modes[i]);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		list.add(ColorHelper.YELLOW + "Current Mode: " + modes[stack.getItemDamage()]);
	}
	
	public static String[] modes = {"Default", "Rotation", "Block Remover", "Upgrade Remover"};

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			
			if(player.isSneaking()) {
				
				TileEntity tileEntity = world.getTileEntity(x, y, z);
			
				if(tileEntity != null) {
					HTSM.proxy.playSound("ItemUniversalMultiTool", x, y, z, 1.0f, 1.0f);
					switch(itemStack.getItemDamage()) {
						case 0: break;
						case 1: {
							if(tileEntity instanceof ITileEntityRotatable) {
								handleRotateRotatable(itemStack, world, player, x, y, z, meta, hitX, hitY, hitZ);
							}
							break;
						}
						case 2: {
							if(tileEntity instanceof TileEntityCage) { 
								handleTileEntityCage(itemStack, world, player, x, y, z, meta, hitX, hitY, hitZ);
							}
							break;
						}
						case 3: {
							 if(tileEntity instanceof ITileEntityUpgradable) {
									handleRemoveUpgrade(itemStack, world, player, x, y, z, meta, hitX, hitY, hitZ);
								}
							}
							break;
					}
				}
				
			}
		}
		return false;
	}
	private void handleRemoveUpgrade(ItemStack itemStack, World world, EntityPlayer player, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		ITileEntityUpgradable tileEntity = (ITileEntityUpgradable) world.getTileEntity(x, y, z);
		
		for(int i = 0; i < tileEntity.getSizeUpgradeInventory(); i++) {
			ItemStack stack = tileEntity.getStackInUpgradeSlot(i);
			
			if(stack != null) {
				EntityItem entity = new EntityItem(world, x, y, z, stack);
				tileEntity.setUpgradeInventorySlotContents(i, null);
				world.spawnEntityInWorld(entity);
			}
		}
	}
	
	private void handleRotateRotatable(ItemStack itemStack, World world, EntityPlayer player, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		ITileEntityRotatable tileEntity = (ITileEntityRotatable) world.getTileEntity(x, y, z);
		
		ForgeDirection UP = ForgeDirection.UP;
		ForgeDirection DOWN = ForgeDirection.DOWN;
		ForgeDirection NORTH = ForgeDirection.NORTH;
		ForgeDirection SOUTH = ForgeDirection.SOUTH;
		ForgeDirection EAST = ForgeDirection.EAST;
		ForgeDirection WEST = ForgeDirection.WEST;
		
		if(tileEntity.getOrientation() == UP)
			tileEntity.setOrientation(ForgeDirection.DOWN);
		else if(tileEntity.getOrientation() == DOWN)
			tileEntity.setOrientation(ForgeDirection.NORTH);
		else if(tileEntity.getOrientation() == NORTH)
			tileEntity.setOrientation(ForgeDirection.SOUTH);
		else if(tileEntity.getOrientation() == SOUTH)
			tileEntity.setOrientation(ForgeDirection.EAST);
		else if(tileEntity.getOrientation() == EAST)
			tileEntity.setOrientation(ForgeDirection.WEST);
		else if(tileEntity.getOrientation() == WEST)
			tileEntity.setOrientation(ForgeDirection.UP);
		
		HTSM.ch.INSTANCE.sendToAll(new PacketSpikeData((byte) tileEntity.getOrientation().ordinal(), x, y, z));
	}
	
	private void handleTileEntityCage(ItemStack itemStack, World world, EntityPlayer player, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		
		ITileEntityRemovable tileEntity = (ITileEntityRemovable) world.getTileEntity(x, y, z);
				
		EntityItem entityItem = new EntityItem(world, x, y, z, tileEntity.removeTileEntity());
		
		entityItem.setVelocity(0.0d, 0.0d, 0.0d);
		
		if(player.inventory.getFirstEmptyStack() > -1)
			entityItem.setPosition(player.posX, player.posY, player.posZ);
		
		world.spawnEntityInWorld(entityItem);
	}
	
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }
}
