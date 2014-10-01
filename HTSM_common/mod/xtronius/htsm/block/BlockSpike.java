package mod.xtronius.htsm.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.lib.RenderTypes;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockSpike extends HTSMBlockContainer {

	public BlockSpike() {
		super(Material.iron);
		this.setBlockTextureName("BlockSpike");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
		this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.375f, 0.9375f);
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.425f, 0.9375f);
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
//		return null;
	}
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.attackEntityFrom(DamageSource.generic, (float) (1.0f + Math.abs((2.0f * entity.fallDistance))));
	}
	
	public TileEntity createNewTileEntity(World world, int var) { return new TileEntitySpike(); }

	public int getRenderType() { return RenderTypes.BLOCK_SPIKE; }
	public boolean isOpaqueCube() { return false; }
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }
	public boolean renderAsNormalBlock() { return false; }
}
