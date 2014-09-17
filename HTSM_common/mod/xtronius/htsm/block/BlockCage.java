package mod.xtronius.htsm.block;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.gui.GuiIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCage extends HTSMBlockContainer{

	public BlockCage() {
		super(Material.glass);
		this.setBlockTextureName("BlockCage");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
	}
	
	public TileEntity createNewTileEntity(World par1World, int var2) { return new TileEntityCage(); }
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	if(!world.isRemote) {
	    	if(player.isSneaking())  {
	    		
	    		if(player.inventory != null && player.inventory.getCurrentItem() == null) {
//	    			if(player.inventory.getCurrentItem().getItem().equals(HTSM.itemInit.getItemByName("ItemUniversalMultiTool"))) {
	    				
	    				TileEntityCage tileEntity = (TileEntityCage) world.getTileEntity(x, y, z);
	    				
	    				ItemStack stack = new ItemStack(HTSM.itemInit.getItemByName("ItemCage")); 
	    				
	    				stack.setTagCompound(new NBTTagCompound());
	    				
	    				NBTTagCompound nbt = stack.stackTagCompound;
	    				
	    				NBTTagList list = new NBTTagList();
	    		        
	    		        for (int i = 0; i < 3; ++i) {
	    		            if (tileEntity.getStackInSlot(i) != null) {
	    		                NBTTagCompound compound1 = new NBTTagCompound();
	    		                compound1.setByte("Slot", (byte)i);
	    		                tileEntity.getStackInSlot(i).writeToNBT(compound1);
	    		                list.appendTag(compound1);
	    		            }
	    		        }
	    		        
	    		        nbt.setTag("CageItems", list);
	    		        
	    		        if(tileEntity.getEntityData() != null) {
	    			        nbt.setTag("EntityData", tileEntity.getEntityData());
	    		        }
	    		        
	    				player.inventory.addItemStackToInventory(stack);
	    				
	    				world.setBlockToAir(x, y, z);
//	    			}
	    		}
	    		
    	    	return true;
	    	} else {
		    	FMLNetworkHandler.openGui(player, HTSM.instance, GuiIDs.guiCageID, world, x, y, z);
		    	return true;
    	    }
	    }
    	return true;
    }
	
	public void onBlockPreDestroy(World world, int x, int y, int z, int oldMeta) {
		TileEntityCage tileEntity = (TileEntityCage) world.getTileEntity(x, y, z);
		
		if(tileEntity != null) {
			tileEntity.releaseEntity();
		}
	}
	
	public void onBlockAdded(World world, int x, int y, int z) { super.onBlockAdded(world, x, y, z); }
	
	public int getRenderType() { return -1; }
	public boolean isOpaqueCube() { return false; }
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }
	public boolean renderAsNormalBlock() { return false; }
}
