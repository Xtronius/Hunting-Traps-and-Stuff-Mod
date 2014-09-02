package mod.xtronius.htsm.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TestBlock extends Block {
	
	public TestBlock() {
		super(Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
