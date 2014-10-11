package mod.xtronius.htsm.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.*;
import mod.xtronius.htsm.item.gun.*;
import mod.xtronius.htsm.item.gun.ammo.ItemAmmo;
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
		addItem(new ItemUniversalMultiTool(), "ItemUniversalMultiTool", true);
		addItem(new ItemCage(), "ItemCage", true);
		addItem(new ItemUpgrade(), "ItemUpgrade", false);
		addItem(new ItemGun(new ItemShotgun()), "ItemShotGun", true);
		addItem(new ItemAmmo(3), "ItemShotGunAmmo", true);
		addItem(new ItemAmmoBag(), "ItemAmmoBag", true);
	}
	
	private void addItem(Item item, String name, boolean addItemToCreativeTab) {
		item.setUnlocalizedName(name);
		item.setTextureName(Reference.MOD_ASSET + ':' + name);
		if(addItemToCreativeTab) item.setCreativeTab(HTSM.tabItems);
		ItemIDs.genNewItemIDObj(name); 
		itemNames.add(name); 
		items.put(name, item); 
	}
	
//	public static void addToItemReg(String name) { Item.itemRegistry.addObject(ItemIDs.getItemID(name), name, items.get(name)); }
	public static Item getItemByName(String name) { return items.get(name); }
}