package mod.xtronius.htsm.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import mod.xtronius.htsm.block.BlockIDs;
import mod.xtronius.htsm.block.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class HTSMBlockInitializer {
	
	public static ArrayList<String> blockNames = new ArrayList<String>();
	public static HashMap<String, Block> blocks = new HashMap<String, Block>();
	
	public static HTSMBlockInitializer instance;

	public HTSMBlockInitializer() {
		instance = this;
		init();
	}
	
	private void init() {}
	
	private void addBlock(Block block, String name) { 
		block.setBlockName(name); 
		BlockIDs.genNewBlockIDObj(name); 
		blockNames.add(name); 
		blocks.put(name, block); 
	}
	
	public static void addToBlockReg(String name) { Block.blockRegistry.addObject(BlockIDs.getBlockID(name), name, blocks.get(name)); }
}