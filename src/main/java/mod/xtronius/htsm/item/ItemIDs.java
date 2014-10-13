package mod.xtronius.htsm.item;

import java.util.HashMap;

import net.minecraft.item.Item;

public class ItemIDs {
	
	private static HashMap<String, Integer> itemIDs = new HashMap<String, Integer>();
	
	public static void genNewItemIDObj(String name) { itemIDs.put(name + "ID", null); }
	public static int getItemID(String name) { return itemIDs.get(name + "ID"); }
	public static void setItemID(String name, int id) { itemIDs.put(name + "ID", id); }
}
