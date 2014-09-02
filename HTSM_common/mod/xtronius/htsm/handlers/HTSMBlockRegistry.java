package mod.xtronius.htsm.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import mod.xtronius.htsm.core.HTSM;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class HTSMBlockRegistry {
	
	public HTSMBlockRegistry() {
		for(String name : HTSM.blockInit.blockNames) { 
			Block block = HTSM.blockInit.blocks.get(name);
			regBlockAuto(block, name);
		}
		
		regBlockManual();
	}

	private void regBlockAuto(Block block, String name) {	
		GameRegistry.registerBlock(block, name);
		
		GameRegistry.addShapelessRecipe(new ItemStack(block, 64), new ItemStack(Blocks.dirt));
	}
	
	private void regBlockManual() {
//		GameRegistry.registerTileEntity(TileEntityExample.class, "BlockExample");
	}
}
