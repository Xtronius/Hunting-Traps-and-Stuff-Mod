package mod.xtronius.htsm.handlers;

import java.io.File;

import mod.xtronius.htsm.block.BlockIDs;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.ItemIDs;
import mod.xtronius.htsm.lib.ConfigValues;
import mod.xtronius.htsm.lib.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
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

		for(String name : HTSM.htsmBlock.blockNames) BlockIDs.setBlockID(name, config.get(Reference.MOD_OPTIONS, name + "ID", idB++).getInt());  
		for(String name : HTSM.htsmItem.itemNames) ItemIDs.setItemID(name, config.get(Reference.MOD_OPTIONS, name + "ID", idI++).getInt()); 	
	}

	private static void HandleConfigOptions() {
		config.addCustomCategoryComment(Reference.MOD_OPTIONS, "Gun Options");
		ConfigValues.RenderBullet = initBoolean("Should Render Bullet", false, "Determines whether or not to render the entity bullet.");
		ConfigValues.RenderBlood = initBoolean("Should Render Blood", true, "Determines whether or not to render blood.");
		ConfigValues.RenderBlockHitParticles = initBoolean("Should Render Block Hit By Bullet Particles", true, "Determines whether or not to render the block hit by bullet particles.");
		ConfigValues.PlayGunFireSound = initBoolean("Should Play Gun Fire Sound", true, "Determines whether or not to play the gun fire sound");
		ConfigValues.PlayGunReloadSound = initBoolean("Should Play Gun Reload Sound", true, "Determines whether or not to play the gun reload sound");
		ConfigValues.BulletSize = initFloat("Bullet Size", 0.125F, 0.0F, 1.0F, "Scaling From 0.0F - 1.0F, this value determines the size of the bullet, assuming that rendering entity bullet is enabled.", "bullet.size");
		config.addCustomCategoryComment(Reference.MOD_OPTIONS, "Cage Options");
		ConfigValues.RenderCageGateAnimation = initBoolean("Should Render Cage Gate Animation", true, "Determines whether or not to render the cage gate animation");
		ConfigValues.RenderCageEntityRotationAnimation = initBoolean("Should Render Cage Entity Rotation Animation", true, "Determines whether or not to render the cage entity rotation animation");
		ConfigValues.cageUpdateRefreshRate = initFloat("Cage Refresh Rate - Seconds", 0.75F, 0.1F, 15.0F, "Scaling From 0.1F - 15.0F, this value determines the refresh rate of the cage block. NOTE: Smaller values cause more lag on low end PCs & the refresh rate may effect the latency of the the time when the gate button is pressed to the time it reacts!", "cage.refreshRate");
		config.addCustomCategoryComment(Reference.MOD_OPTIONS, "Plaque Options");
		ConfigValues.plaqueUpdateRefreshRate = initFloat("Plaque Refresh Rate - Seconds", 5.0F, 0.1F, 15.0F, "Scaling From 0.1F - 15.0F, this value determines the refresh rate of the plaque block. NOTE: Smaller values cause more lag on low end PCs!", "plaque.refreshRate");
		config.addCustomCategoryComment(Reference.MOD_OPTIONS, "Spike Options");
		ConfigValues.spikeUpdateRefreshRate = initFloat("Spike Refresh Rate - Seconds", 5.0F, 0.1F, 15.0F, "Scaling From 0.1F - 15.0F, this value determines the refresh rate of the spike block. NOTE: Smaller values cause more lag on low end PCs!", "spike.refreshRate");
	}
	
	private static boolean initBoolean(String key, boolean defaultValue, String description) {
		return config.getBoolean(key, Reference.MOD_OPTIONS, defaultValue, description);
	}
	
	private static int initInt(String key, int defaultValue, int min, int max, String description, String langKey) {
		return config.getInt(key, Reference.MOD_OPTIONS, defaultValue, min, max, description, langKey);
	}
	
	private static float initFloat(String key, float defaultValue, float min, float max, String description, String langKey) {
		return config.getFloat(key, Reference.MOD_OPTIONS, defaultValue, min, max, description, langKey);
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
