package mod.xtronius.htsm.util;

import cpw.mods.fml.common.FMLCommonHandler;

public class SideHelper {
	public static boolean isServer() {
	    return FMLCommonHandler.instance().getEffectiveSide().isServer();
	  }

	  public static boolean isClient() {
	    return FMLCommonHandler.instance().getEffectiveSide().isClient();
	  }
}
