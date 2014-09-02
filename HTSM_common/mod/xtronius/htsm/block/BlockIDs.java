package mod.xtronius.htsm.block;

import java.util.HashMap;

public class BlockIDs {

	//public static int airID;
	
	private static HashMap<String, Integer> blockIDs = new HashMap<String, Integer>();
	
	public static void genNewBlockIDObj(String name) { blockIDs.put(name + "ID", null); }
	public static int getBlockID(String name) { for(int i = 0; i < 100; i++) System.out.println(blockIDs.get(name + "ID"));return blockIDs.get(name + "ID"); }
	public static void setBlockID(String name, int id) { blockIDs.put(name + "ID", id); }
}
