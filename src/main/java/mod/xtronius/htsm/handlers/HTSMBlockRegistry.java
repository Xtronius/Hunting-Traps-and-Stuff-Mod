package mod.xtronius.htsm.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class HTSMBlockRegistry {
	
	public HTSMBlockRegistry() {
		for(String name : HTSM.htsmBlock.blockNames) { 
			Block block = HTSM.htsmBlock.blocks.get(name);
			regBlockAuto(block, name);
		}
		
		regBlockManual();
	}

	private void regBlockAuto(Block block, String name) {	
		GameRegistry.registerBlock(block, name);
	}
	
	private void regBlockManual() {
//		GameRegistry.registerTileEntity(TileEntityExample.class, "BlockExample");
		GameRegistry.registerTileEntity(TileEntityCage.class, "BlockCage");
		GameRegistry.registerTileEntity(TileEntityPlaque.class, "BlockPlaque");
		GameRegistry.registerTileEntity(TileEntitySpike.class, "BlockSpike");
	}
}
