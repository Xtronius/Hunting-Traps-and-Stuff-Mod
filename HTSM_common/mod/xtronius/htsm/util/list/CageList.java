package mod.xtronius.htsm.util.list;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Items;
import net.minecraft.item.*;

public class CageList {
	
	private ArrayList<Item> items = new ArrayList<Item>();
	private HashMap<Item, Class> entityList = new HashMap<Item, Class>();

	public CageList() {
		this.addItemEntityPair(Items.carrot, EntityPig.class);
		this.addItemEntityPair(Items.fish, EntityOcelot.class);
		this.addItemEntityPair(Items.bone, EntityWolf.class);
	}
	
	public boolean isValidItem(Item item) { return items.contains(item); }
	public boolean isValidItemStack(ItemStack stack) { return stack != null ? isValidItem(stack.getItem()) : false; }
	
	public boolean isValidItemForEntity(Item item, Class entity) { return (entityList.get(item) != null && entityList.get(item).equals(entity)); }
	public boolean isValidItemStackForEntity(ItemStack stack, Class entity) {  return isValidItemForEntity(stack.getItem(), entity); }

	public Class getEntityFromItem(Item item) { return entityList.get(item) != null ? entityList.get(item) : null; }
	public Class getEntityFromItemStack(ItemStack stack) { return getEntityFromItem(stack.getItem());}
	
	private boolean addItem(Item item) { return item != null ? items.add(item) : false; }
	private boolean addItemStack(ItemStack stack) { return addItem(stack.getItem()); }
	
	private boolean addItemEntityPair(Item item, Class entity) { if(item != null & entity != null) {  entityList.put(item, entity); this.addItem(item); return true; } return false; }
	private boolean addItemStackEntityPair(ItemStack stack, Class entity) { return addItemEntityPair(stack.getItem(), entity); }
}
