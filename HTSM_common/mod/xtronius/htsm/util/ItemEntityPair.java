package mod.xtronius.htsm.util;

import net.minecraft.item.Item;

public class ItemEntityPair {
	
	private Class entity;
	private Item item;

	public ItemEntityPair(Item item, Class entity) {
		this.item = item;
		this.entity = entity;
	}
	
	public Class getEntity() { return entity; }
	public Item getItem() { return item; }
	public void setEntity(Class entity) { this.entity = entity; }
	public void setItem(Item item) {this.item = item; }
}
