package mod.xtronius.htsm.handlers;

import mod.xtronius.htsm.block.BlockIDs;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.ItemIDs;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class HTSMIDHandler {
	 
	public static void RegConfigIDs(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        int idI = 20000;
        int idB = 2750;
        int idGui = 1;

        
        for(String name : HTSM.blockInit.blockNames) BlockIDs.setBlockID(name, config.get("BlockIDs", name + "ID", idB++).getInt());  
        for(String name : HTSM.itemInit.itemNames) ItemIDs.setItemID(name, config.get("ItemIDs", name + "ID", idI++).getInt()); 
		  
		  config.save();
	}
}
