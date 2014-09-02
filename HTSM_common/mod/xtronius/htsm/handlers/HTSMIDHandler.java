package mod.xtronius.htsm.handlers;

import mod.xtronius.htsm.block.BlockIDs;
import mod.xtronius.htsm.block.HTSMBlock;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class HTSMIDHandler {
	 
	public static void RegConfigIDs(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        int idI = 20000;
        int idB = 2750;
        int idGui = 1;
        
//		  BlockIDs.airID = config.get("AirID", "AirID", idB++).getInt();
        
        for(String name : HTSMBlock.blockNames) { BlockIDs.setBlockID(name, config.get(name + "ID", name + "ID", idB++).getInt()); }
		  
		  config.save();
	}
}
