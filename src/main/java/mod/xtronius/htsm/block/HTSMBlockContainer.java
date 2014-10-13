package mod.xtronius.htsm.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class HTSMBlockContainer extends BlockContainer{

	public HTSMBlockContainer(Material material) {
		super(material);
	}

	@Override
	@SideOnly(Side.CLIENT)

	public void registerBlockIcons(IIconRegister iconRegister) {
		 blockIcon = iconRegister.registerIcon("hts_mod:" + this.textureName);
	}
}
