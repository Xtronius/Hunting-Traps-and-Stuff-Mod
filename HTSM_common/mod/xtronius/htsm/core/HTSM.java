package mod.xtronius.htsm.core;

import mod.xtronius.htsm.handlers.HTSMBlockInitializer;
import mod.xtronius.htsm.handlers.HTSMBlockRegistry;
import mod.xtronius.htsm.handlers.HTSMIDHandler;
import mod.xtronius.htsm.handlers.HTSMItemInitializer;
import mod.xtronius.htsm.handlers.HTSMItemRegistry;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.proxy.CommonProxy;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class HTSM {
	
	public HTSM() {}
	
	@Instance(Reference.MOD_ID)
	public static HTSM instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	
	public static CommonProxy proxy;
	public static HTSMBlockInitializer blockInit = HTSMBlockInitializer.instance;
	public static HTSMItemInitializer itemInit = HTSMItemInitializer.instance;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		new HTSMBlockInitializer();
		new HTSMItemInitializer();
		HTSMIDHandler.RegConfigIDs(event);
		
		for(String name : blockInit.blockNames)
			blockInit.addToBlockReg(name);
		for(String name : itemInit.itemNames)
			itemInit.addToItemReg(name);	
    }   
    
	@EventHandler
	public void init(FMLInitializationEvent event) {
		new HTSMBlockRegistry();
		HTSMItemRegistry.ItemReg();
//		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
    	
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.registerRenderInformation();
    	proxy.initSounds();
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
    	 MinecraftServer server = MinecraftServer.getServer();
    	 ICommandManager command = server.getCommandManager();
    	 ServerCommandManager manager = (ServerCommandManager) command;
//    	 manager.registerCommand(new ResetLvlNBT());
    }
}

