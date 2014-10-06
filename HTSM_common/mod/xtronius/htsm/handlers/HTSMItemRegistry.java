package mod.xtronius.htsm.handlers;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class HTSMItemRegistry {
	
	public HTSMItemRegistry() {
		for(String name : HTSM.itemInit.itemNames) { 
			Item item = HTSM.itemInit.getItemByName(name);
			regItemAuto(item, name);
		}
		
		regItemManual();
	}

	private void regItemAuto(Item item, String name) {	
		GameRegistry.registerItem(item, name);
	}
	
	private void regItemManual() {}
}
