package mod.xtronius.htsm.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import mod.xtronius.htsm.block.BlockCage;
import mod.xtronius.htsm.block.BlockFallTrap;
import mod.xtronius.htsm.block.BlockIDs;
import mod.xtronius.htsm.block.BlockPlaque;
import mod.xtronius.htsm.block.BlockSpike;
import mod.xtronius.htsm.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

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
		addBlock(new BlockFallTrap(), "BlockFallTrap");
	}
	
	private void addBlock(Block block, String name) { 
		block.setBlockName(name); 
		BlockIDs.genNewBlockIDObj(name); 
		blockNames.add(name); 
		blocks.put(name, block); 
	}
	
	public static Block getBlockByName(String name) { return blocks.get(name); }
	public static Item getBlockAsItemByName(String name) { return GameRegistry.findItem(Reference.MOD_ID, name); }
	public static Item getBlockAsItemByBlock(Block block) { return GameRegistry.findItem(Reference.MOD_ID, block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1)); }
}