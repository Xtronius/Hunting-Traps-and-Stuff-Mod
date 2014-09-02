package mod.xtronius.htsm.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import mod.xtronius.htsm.block.HTSMBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class HTSMBlockRegistry {
	
	public HTSMBlockRegistry() {
		for(String name : HTSMBlock.blockNames) { 
			Block block = HTSMBlock.blocks.get(name);
			regBlock(block, name);
		}
	}

	public static void regBlock(Block block, String name) {	
		GameRegistry.registerBlock(block, name);
		
		GameRegistry.addShapelessRecipe(new ItemStack(block, 64), new ItemStack(Blocks.dirt));
	}
}
