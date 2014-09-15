package mod.xtronius.htsm.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.gui.GuiIDs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCage extends HTSMBlockContainer{

	public BlockCage() {
		super(Material.glass);
		this.setBlockTextureName("BlockCage");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public TileEntity createNewTileEntity(World par1World, int var2) { return new TileEntityCage(); }
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	if(!world.isRemote) {
	    	if(player.isSneaking())  {
    	    	return true;
	    	} else {
		    	FMLNetworkHandler.openGui(player, HTSM.instance, GuiIDs.guiCageID, world, x, y, z);
		    	return true;
    	    }
	    }
    	return true;
    }
	
	public int getRenderType() { return -1; }
	
	public boolean isOpaqueCube() { return false; }
	
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 0; }
	public boolean renderAsNormalBlock() { return false; }
}
