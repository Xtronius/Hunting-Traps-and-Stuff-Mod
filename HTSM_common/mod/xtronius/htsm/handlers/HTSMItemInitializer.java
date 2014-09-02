package mod.xtronius.htsm.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import mod.xtronius.htsm.item.ItemIDs;
import mod.xtronius.htsm.item.*;
import net.minecraft.item.Item;


public class HTSMItemInitializer {

	public static ArrayList<String> itemNames = new ArrayList<String>();
	public static HashMap<String, Item> items = new HashMap<String, Item>();
	
	public static HTSMItemInitializer instance;

	public HTSMItemInitializer() {
		instance = this;
		init();
	}
	
	private void init() {}
	
	private void addItem(Item item, String name) { 
		ItemIDs.genNewItemIDObj(name); 
		itemNames.add(name); 
		items.put(name, item); 
	}
	
	public static void addToItemReg(String name) { Item.itemRegistry.addObject(ItemIDs.getItemID(name), name, items.get(name)); }
}