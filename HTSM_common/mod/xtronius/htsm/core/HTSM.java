package mod.xtronius.htsm.core;

import mod.xtronius.htsm.CreativeTab.*;
import mod.xtronius.htsm.handlers.*;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.proxy.IProxy;
import mod.xtronius.htsm.util.Debug;
import mod.xtronius.htsm.util.list.CageList;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class HTSM {
	
	public HTSM() {}
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	
	public static IProxy proxy;
	public static HTSMBlockInitializer htsmBlock = HTSMBlockInitializer.INSTANCE;
	public static HTSMItemInitializer htsmItem = HTSMItemInitializer.INSTANCE;
	
	public static CreativeTabs tabItems = new CreativeTabHTSMItems(CreativeTabs.getNextID(), "HTSM Items");
	public static CreativeTabs tabBlocks = new CreativeTabHTSMBlocks(CreativeTabs.getNextID(), "HTSM Blocks");
	
	public static PacketHandler ch = new PacketHandler();
	
	public static Debug debug = new Debug();
	
	@Instance(Reference.MOD_ID)
	public static HTSM INSTANCE;
	
	public static CageList cageList;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		proxy.initPacketInfo();
		proxy.registerEntities();
		
		new HTSMBlockInitializer();
		new HTSMItemInitializer();
		HTSMConfigHandler.inits(event.getSuggestedConfigurationFile());
		
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
    	proxy.registerKeybindings();
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

