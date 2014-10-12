package mod.xtronius.htsm.handlers;

import java.io.File;

import mod.xtronius.htsm.block.BlockIDs;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.ItemIDs;
import mod.xtronius.htsm.lib.ConfigCategories;
import mod.xtronius.htsm.lib.ConfigValues;
import mod.xtronius.htsm.lib.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HTSMConfigHandler {
    public static Configuration config;

    public static void inits(File configFile) {
        if (config == null) {
        	config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
		HandleConfigIDs();
		HandleConfigOptions();
		

        if (config.hasChanged())
        	config.save();
    }
    
	private static void HandleConfigIDs() {
		int idI = 20000;
		int idB = 2750;
		int idGui = 1;

		for(String name : HTSM.htsmBlock.blockNames) BlockIDs.setBlockID(name, config.get(ConfigCategories.BLOCKIDS, name + "ID", idB++).getInt());  
		for(String name : HTSM.htsmItem.itemNames) ItemIDs.setItemID(name, config.get(ConfigCategories.ITEMIDS, name + "ID", idI++).getInt()); 	
	}

	private static void HandleConfigOptions() {
		config.addCustomCategoryComment(ConfigCategories.RENDERING, "These value decide wheather or not to render and obj or particle or they control how to render an object or particle");
		ConfigValues.RenderBullet = config.get(Configuration.CATEGORY_GENERAL, "ShouldRenderBullet", ConfigValues.RenderBullet).getBoolean();
	}

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
            loadConfiguration();
    }
}

////TODO Fix Config Handler
//
//public class HTSMConfigHandler {
//	
//	public static HTSMConfigHandler INSTANCE;
//	private Configuration config;
//	
//	public HTSMConfigHandler(FMLPreInitializationEvent event) {
//		INSTANCE = this;
//		loadConfiguration(event.getSuggestedConfigurationFile());
//	}
//	
//	public HTSMConfigHandler() { 
//		INSTANCE = this; 
//	}
//	
//	public void loadConfiguration(File configFile) {
//		if(config == null)
//			config = new Configuration(configFile);
//		
//		HandleConfigIDs(config);
//		HandleConfigOptions(config);
//		
//		if(config.hasChanged())
//			config.save();
//	}
//	 
//	private void HandleConfigIDs(Configuration config) {
//        int idI = 20000;
//        int idB = 2750;
//        int idGui = 1;
//
//        for(String name : HTSM.htsmBlock.blockNames) BlockIDs.setBlockID(name, config.get(ConfigCategories.BLOCKIDS, name + "ID", idB++).getInt());  
//        for(String name : HTSM.htsmItem.itemNames) ItemIDs.setItemID(name, config.get(ConfigCategories.ITEMIDS, name + "ID", idI++).getInt()); 	
//	}
//	
//	private void HandleConfigOptions(Configuration config) {
//		config.addCustomCategoryComment(ConfigCategories.RENDERING, "These value decide wheather or not to render and obj or particle or they control how to render an object or particle");
//		ConfigValues.RenderBullet = config.get("Rendering", "ShouldRenderBullet", ConfigValues.RenderBullet).getBoolean();
//	}
//	
//	@SubscribeEvent
//    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
//        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
////            loadConfiguration();
//        }
//    }
//}
