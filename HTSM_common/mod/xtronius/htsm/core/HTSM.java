package mod.xtronius.htsm.core;

import java.util.EnumMap;

import mod.xtronius.htsm.handlers.*;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.packet.*;
import mod.xtronius.htsm.proxy.*;
import mod.xtronius.htsm.util.Debug;
import mod.xtronius.htsm.util.list.*;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class HTSM {
	
	public HTSM() {}
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	
	public static IProxy proxy;
	public static HTSMBlockInitializer blockInit = HTSMBlockInitializer.instance;
	public static HTSMItemInitializer itemInit = HTSMItemInitializer.instance;
	
	
	public static PacketHandler ch = new PacketHandler();
	
	public static Debug debug = new Debug();
	
	@Instance(Reference.MOD_ID)
	public static HTSM instance;
	
	public static CageList cageList;
	public static ModelCageList modelCageList;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		proxy.initPacketInfo();
		
		new HTSMBlockInitializer();
		new HTSMItemInitializer();
		HTSMIDHandler.RegConfigIDs(event);
		
		new HTSMEventInitializer();
    }   
    
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		new HTSMBlockRegistry();
		new HTSMItemRegistry();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
    	
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.initRenderingAndTextures();
    	proxy.initSounds();
    	proxy.initMiscInfo();
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
    	 MinecraftServer server = MinecraftServer.getServer();
    	 ICommandManager command = server.getCommandManager();
    	 ServerCommandManager manager = (ServerCommandManager) command;
//    	 manager.registerCommand(new ResetLvlNBT());
    }
}

