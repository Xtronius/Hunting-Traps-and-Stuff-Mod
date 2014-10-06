package mod.xtronius.htsm.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.damageSource.HTSMDamageSource;
import mod.xtronius.htsm.lib.RenderTypes;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import mod.xtronius.htsm.util.ColorHelper;
import mod.xtronius.htsm.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSpike extends HTSMBlockContainer {

	public BlockSpike() {
		super(Material.iron);
		this.setBlockTextureName("BlockSpike");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
		this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.375f, 0.9375f);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			ItemStack stack = player.getCurrentEquippedItem();
			if((stack != null) && (stack.getItem() == HTSM.itemInit.getItemByName("ItemUpgrade")) && (stack.getItemDamage() != 0)) {
				TileEntitySpike tileEntity = (TileEntitySpike) world.getTileEntity(x, y, z);
				
				if(tileEntity != null) {
					for(int i = 0; i < tileEntity.getSizeInventory(); i++) {
						if(tileEntity.getStackInSlot(i) == null) {
							tileEntity.setInventorySlotContents(i, stack);
							player.setCurrentItemOrArmor(0, null);
							Util.sendPlayerMessage(player, ColorHelper.GREEN + "Upgrade Inserted");
							break;
						} else {
							Util.sendPlayerMessage(player, ColorHelper.DARK_RED + "There are no more upgrade slots available.");
						}
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	 public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		 TileEntitySpike tileEntity = (TileEntitySpike) blockAccess.getTileEntity(x, y, z);
		 
		 if(tileEntity != null) {
			 ForgeDirection orientation = tileEntity.getOrientation();
			 
			 switch(orientation) {
			 	case UP:
			 	{
			 		this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.375f, 0.9375f);
			 		return;
			 	}
			 	case DOWN:
			 	{
			 		this.setBlockBounds(0.0625f, 0.625f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
			 		return;
			 	}
			 	case SOUTH:
			 	{
			 		this.setBlockBounds(0.0625f, 0.0625f, 0.0f, 0.9375f, 0.9375f, 0.375f);
			 		return;
			 	}
			 	case NORTH:
			 	{
			 		this.setBlockBounds(0.0625f, 0.0625f, 0.625f, 0.9375f, 0.9375f, 1.0f);
			 		return;
			 	}
			 	case EAST:
			 	{
			 		this.setBlockBounds(0.0f, 0.0625f, 0.0625f, 0.375f, 0.9375f, 0.9375f);
			 		return;
			 	}
			 	case WEST:
			 	{
			 		this.setBlockBounds(0.625f, 0.0625f, 0.0625f, 1.0f, 0.9375f, 0.9375f);
			 		return;
			 	}
			 	case UNKNOWN:
			 	{
			 		return;
			 	}
			 	default: return;
			 }
		 }
	 }
	
	 @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
//		return null;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(!((entity instanceof EntityItem) || (entity instanceof EntityIronGolem))) {
			float damage = (float) (1.0f + Math.abs((2.0f * entity.fallDistance)));
			if(entity.fallDistance == 0 && entity.isSneaking()) damage = damage/2;
			entity.attackEntityFrom(HTSMDamageSource.spike, damage);
		}
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntitySpike) {
            ((TileEntitySpike) world.getTileEntity(x, y, z)).setOrientation(world.getBlockMetadata(x, y, z));
        }

        world.setBlockMetadataWithNotify(x, y, z, 0, 3);
    }
	
	 @Override
    public int onBlockPlaced(World world, int x, int y, int z, int sideHit, float hitX, float hitY, float hitZ, int metaData) {
        return sideHit;
    }
	 @Override
	public TileEntity createNewTileEntity(World world, int var) { return new TileEntitySpike(); }
	@Override
	public int getRenderType() { return RenderTypes.BLOCK_SPIKE; }
	@Override
	public boolean isOpaqueCube() { return false; }
	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }
	@Override
	public boolean renderAsNormalBlock() { return false; }
}
