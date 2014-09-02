package mod.xtronius.htsm.block;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class HTSMBlock {
	
	public static ArrayList<String> blockNames = new ArrayList<String>();
	public static HashMap<String, Block> blocks = new HashMap<String, Block>();

	public HTSMBlock() {
		addBlock(new TestBlock().setCreativeTab(CreativeTabs.tabBlock), "TestBlock");
	}
	
	private void addBlock(Block block, String name) { 
		block.setBlockName(name); 
		BlockIDs.genNewBlockIDObj(name); 
		blockNames.add(name); 
		blocks.put(name, block); }
}
