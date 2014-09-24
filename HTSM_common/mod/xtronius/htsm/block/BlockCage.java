package mod.xtronius.htsm.block;

import java.util.Random;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.gui.GuiIDs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCage extends HTSMBlockContainer{
	
	private Random rand = new Random();

	public BlockCage() {
		super(Material.iron);
		this.setBlockTextureName("BlockCage");
		this.setLightOpacity(0);
	}
	
	public TileEntity createNewTileEntity(World par1World, int var2) { return new TileEntityCage(); }
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	if(!world.isRemote) {
	    	if(player.isSneaking())  {
	    		
//	    		if(player.inventory != null && player.inventory.getCurrentItem() == null) {
////	    			if(player.inventory.getCurrentItem().getItem().equals(HTSM.itemInit.getItemByName("ItemUniversalMultiTool"))) {
//	    				
//	    				TileEntityCage tileEntity = (TileEntityCage) world.getTileEntity(x, y, z);
//	    				
//	    				ItemStack stack = new ItemStack(HTSM.itemInit.getItemByName("ItemCage")); 
//	    				
//	    				stack.setTagCompound(new NBTTagCompound());
//	    				
//	    				NBTTagCompound nbt = stack.stackTagCompound;
//	    				
//	    				NBTTagList list = new NBTTagList();
//	    		        
//	    		        for (int i = 0; i < 3; ++i) {
//	    		            if (tileEntity.getStackInSlot(i) != null) {
//	    		                NBTTagCompound compound1 = new NBTTagCompound();
//	    		                compound1.setByte("Slot", (byte)i);
//	    		                tileEntity.getStackInSlot(i).writeToNBT(compound1);
//	    		                list.appendTag(compound1);
//	    		            }
//	    		        }
//	    		        
//	    		        nbt.setTag("CageItems", list);
//	    		        
//	    		        if(tileEntity.getEntityData() != null) {
//	    			        nbt.setTag("EntityData", tileEntity.getEntityData());
//	    		        }
//	    		        
//	    				player.inventory.addItemStackToInventory(stack);
//	    				
//	    				for(int i = 0; i < tileEntity.getSizeInventory(); i++)
//    						tileEntity.setInventorySlotContents(i, null);;
//    				
//    					tileEntity.setEntityData(null);
//    					tileEntity.releaseEntity();
//	    				
//	    				world.setBlockToAir(x, y, z);
////	    			}
//	    		}
	    		
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
			
			for (int i1 = 0; i1 < tileEntity.getSizeInventory(); ++i1) {
                ItemStack itemstack = tileEntity.getStackInSlot(i1);

                if (itemstack != null) {
                    float f = this.rand.nextFloat() * 0.9F + 0.2F;
                    float f1 = this.rand.nextFloat() * 0.9F + 0.2F;
                    float f2 = this.rand.nextFloat() * 0.9F + 0.2F;

                    while (itemstack.stackSize > 0) {
                        int j1 = this.rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)  j1 = itemstack.stackSize;

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                       
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
		}
	}
	
	public void onBlockAdded(World world, int x, int y, int z) { super.onBlockAdded(world, x, y, z); }
	
	public boolean canProvidePower() {
        return true;
    }
	
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int meta) {
		TileEntityCage tileEntity = (TileEntityCage) blockAccess.getTileEntity(x, y, z);
		if(!tileEntity.getWorldObj().isRemote) 
	        if(tileEntity != null) 
	        	if(tileEntity.getEntityData() != null) return 15;
		return 0;
    }
	
	public int getRenderType() { return -1; }
	public boolean isOpaqueCube() { return false; }
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }
	public boolean renderAsNormalBlock() { return false; }
}
