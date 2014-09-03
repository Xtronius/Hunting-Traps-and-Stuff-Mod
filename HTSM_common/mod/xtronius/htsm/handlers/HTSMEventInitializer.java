package mod.xtronius.htsm.handlers;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class HTSMEventInitializer {
	
	public HTSMEventInitializer(){
		
//		FMLCommonHandler.instance().bus().register(new RCTickHandler());
//		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		
		
		MinecraftForge.EVENT_BUS.register(new HTSMEntityHandler());
	}
}
