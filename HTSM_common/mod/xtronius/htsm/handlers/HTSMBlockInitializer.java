package mod.xtronius.htsm.handlers;

import java.util.HashMap;

import mod.xtronius.htsm.block.BlockIDs;
import mod.xtronius.htsm.block.HTSMBlock;
import net.minecraft.block.Block;

public class HTSMBlockInitializer {
	
	private HashMap<String, Block> blocks = new HashMap<String, Block>();

	public HTSMBlockInitializer() {
		//blockRegistry.addObject(0, "air", (new BlockAir()).setBlockName("air"));
		//Block.blockRegistry.addObject(BlockIDs.BlockWood2ID, "BlockWood2", Blocks.Wood2);
	}
	
	private void intitializeBlock(String name) { 
		Block.blockRegistry.addObject(BlockIDs.getBlockID(name), name, HTSMBlock.blocks.get(name));
	}
	
	private void addBlock(String name) { blocks.put(name, null);}
}