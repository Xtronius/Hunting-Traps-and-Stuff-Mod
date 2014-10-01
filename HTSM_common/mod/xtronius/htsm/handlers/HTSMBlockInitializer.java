package mod.xtronius.htsm.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import mod.xtronius.htsm.block.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class HTSMBlockInitializer {
	
	public static ArrayList<String> blockNames = new ArrayList<String>();
	public static HashMap<String, Block> blocks = new HashMap<String, Block>();
	
	public static HTSMBlockInitializer instance;

	public HTSMBlockInitializer() {
		instance = this;
		init();
	}
	
	private void init() {
		addBlock(new BlockCage(), "BlockCage");
		addBlock(new BlockPlaque(), "BlockPlaque");
		addBlock(new BlockSpike(), "BlockSpike");
	}
	
	private void addBlock(Block block, String name) { 
		block.setBlockName(name); 
		BlockIDs.genNewBlockIDObj(name); 
		blockNames.add(name); 
		blocks.put(name, block); 
	}
	
	public static void addToBlockReg(String name) { /*Block.blockRegistry.addObject(BlockIDs.getBlockID(name), name, blocks.get(name)); */}
	public static Block getBlockByName(String name) { return blocks.get(name); }
}