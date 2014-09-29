package mod.xtronius.htsm.block;

import java.util.Random;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.packet.PacketPlaqueData;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import mod.xtronius.htsm.tileEntity.gui.GuiIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPlaque extends HTSMBlockContainer {

	private Random rand = new Random();
	
	public BlockPlaque() {
		super(Material.iron);
		this.setBlockTextureName("BlockCage");
		this.setLightOpacity(0);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
		this.setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
	}
	
	public TileEntity createNewTileEntity(World par1World, int var2) { return new TileEntityPlaque(); }
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	if(!world.isRemote) {
	    	if(player.isSneaking())  {
    	    	return true;
	    	} else {
//		    	FMLNetworkHandler.openGui(player, HTSM.instance, GuiIDs.guiCageID, world, x, y, z);
//		    	System.out.println("working");
	    		TileEntityPlaque tileEntity = (TileEntityPlaque)world.getTileEntity(x, y, z);
	    		
	    		if(tileEntity != null) {
	    			
	    			ItemStack plaqueStack = tileEntity.getStackInSlot(0);
	    			ItemStack playerStack = player.getCurrentEquippedItem();
	    			
	    			if(plaqueStack == null) {
	    				ItemStack stack = playerStack.copy();
	    				if(playerStack.stackSize > 1) {
	    					playerStack.stackSize--;
	    					player.setCurrentItemOrArmor(0, playerStack);
	    					stack.stackSize = 1;
	    					tileEntity.setInventorySlotContents(0, stack);
	    				} else if(playerStack.stackSize == 1) {
	    					tileEntity.setInventorySlotContents(0, stack);
	    					player.setCurrentItemOrArmor(0, null);
	    				} else {
	    					HTSM.debug.printerrln("The stack size should not be less than 1!");
	    				}
	    			} else {
	    				if(playerStack == null) {
	    					EntityItem item = new EntityItem(world, x, y, z, plaqueStack);
	    					tileEntity.setInventorySlotContents(0, null);
	    					world.spawnEntityInWorld(item);
	    				} else {
	    					ItemStack stack = playerStack.copy();
	    					EntityItem item = new EntityItem(world, x, y, z, plaqueStack);
	    					
	    					if(playerStack.stackSize > 1) {
		    					playerStack.stackSize--;
		    					player.setCurrentItemOrArmor(0, playerStack);
		    					stack.stackSize = 1;
		    					tileEntity.setInventorySlotContents(0, stack);
		    				} else if(playerStack.stackSize == 1) {
		    					tileEntity.setInventorySlotContents(0, stack);
		    					player.setCurrentItemOrArmor(0, null);
		    				} else {
		    					HTSM.debug.printerrln("The stack size should not be less than 1!");
		    				}
	    					
	    					world.spawnEntityInWorld(item);
	    					item.setPosition(player.posX, player.posY, player.posZ);
	    				}
	    			}
	    			
					HTSM.ch.getChannel("packetPlaqueData").sendToAll(new PacketPlaqueData(tileEntity.getStackInSlot(0), x, y, z));
	    		}
	    		
	    		return true;
    	    }
	    }
    	return true;
    }
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        if (l == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        if (l == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        if (l == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);

        if (stack.hasDisplayName())  ((TileEntityFurnace)world.getTileEntity(x, y, z)).func_145951_a(stack.getDisplayName());
    }
	
	public void onBlockPreDestroy(World world, int x, int y, int z, int oldMeta) {
		TileEntityPlaque tileEntity = (TileEntityPlaque)world.getTileEntity(x, y, z);
		
		if(tileEntity != null) {
			
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
	
	public boolean canProvidePower() { return true; }
	
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int meta) {
		TileEntityPlaque tileEntity = (TileEntityPlaque)blockAccess.getTileEntity(x, y, z);
		if(!tileEntity.getWorldObj().isRemote)
			if(tileEntity != null)  {
				for(int i = 0; i < tileEntity.getSizeInventory(); ++i) {
						if(tileEntity.getStackInSlot(i) != null) return 15/tileEntity.getStackInSlot(i).stackSize;
				}
			}
		return 0;
    }
	
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
//		if (l == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
//        if (l == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
//        if (l == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
//        if (l == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		
		int meta = blockAccess.getBlockMetadata(x, y, z);
		
		//South
		if(meta == 2) this.setBlockBounds(0.0f, 0.0625f, 0.75f, 1.0f, 0.9375f, 1.0f);
		//North
		if(meta == 3) this.setBlockBounds(0.0f, 0.0625f, 0.0f, 1.0f, 0.9375f, 0.25f);
		//East
		if(meta == 4) this.setBlockBounds(0.75f, 0.0625f, 0.0f, 1.0f, 0.9375f, 1.0f);
		//West
		if(meta == 5) this.setBlockBounds(0.0f, 0.0625f, 0.0f, 0.25f, 0.9375f, 1.0f);
		
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
//		return null;
	}
	
	public int getRenderType() { return -1; }
	public boolean isOpaqueCube() { return false; }
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }
	public boolean renderAsNormalBlock() { return false; }
}
