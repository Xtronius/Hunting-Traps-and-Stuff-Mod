package mod.xtronius.htsm.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import mod.xtronius.htsm.item.*;
import mod.xtronius.htsm.lib.Reference;
import net.minecraft.item.Item;


public class HTSMItemInitializer {

	public static ArrayList<String> itemNames = new ArrayList<String>();
	public static HashMap<String, Item> items = new HashMap<String, Item>();
	
	public static HTSMItemInitializer instance;

	public HTSMItemInitializer() {
		instance = this;
		init();
	}
	
	private void init() {
		addItem(new ItemUniversalMultiTool(), "ItemUniversalMultiTool");
		addItem(new ItemCage(), "ItemCage");
	}
	
	private void addItem(Item item, String name) {
		item.setUnlocalizedName(name);
		item.setTextureName(Reference.MOD_ASSET + ':' + name);
		ItemIDs.genNewItemIDObj(name); 
		itemNames.add(name); 
		items.put(name, item); 
	}
	
	public static void addToItemReg(String name) { Item.itemRegistry.addObject(ItemIDs.getItemID(name), name, items.get(name)); }
	public static Item getItemByName(String name) { return items.get(name); }
}