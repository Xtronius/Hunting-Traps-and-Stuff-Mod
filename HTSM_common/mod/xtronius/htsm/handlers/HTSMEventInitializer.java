package mod.xtronius.htsm.handlers;

import cpw.mods.fml.common.FMLCommonHandler;

public class HTSMEventInitializer {
	
	public HTSMEventInitializer(){
//		FMLCommonHandler.instance().bus().register(new RCTickHandler());
		FMLCommonHandler.instance().bus().register(new HTSMConfigHandler());
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
	}
}
