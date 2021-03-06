package mod.xtronius.htsm.proxy;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.entity.EntityBullet;
import mod.xtronius.htsm.entity.renderer.RenderBullet;
import mod.xtronius.htsm.entity.renderer.model.ModelBullet;
import mod.xtronius.htsm.item.renderer.RenderItemCage;
import mod.xtronius.htsm.item.renderer.RenderItemSpike;
import mod.xtronius.htsm.lib.ConfigValues;
import mod.xtronius.htsm.lib.RenderTypes;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import mod.xtronius.htsm.tileEntity.renderer.RenderCage;
import mod.xtronius.htsm.tileEntity.renderer.RenderPlaque;
import mod.xtronius.htsm.tileEntity.renderer.RenderSpike;
import mod.xtronius.htsm.util.ClientSoundHelper;
import mod.xtronius.htsm.util.KeyBindings;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;


public class ClientProxy extends CommonProxy {
	 
	 @Override
	 public void initRenderingAndTextures() {
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCage.class, new RenderCage());
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlaque.class, new RenderPlaque());
		 MinecraftForgeClient.registerItemRenderer(HTSM.htsmItem.getItemByName("ItemCage"), new RenderItemCage());
		 MinecraftForgeClient.registerItemRenderer(HTSM.htsmBlock.getBlockAsItemByName("BlockSpike"), new RenderItemSpike());
		 
		 RenderTypes.BLOCK_SPIKE = RenderingRegistry.getNextAvailableRenderId();
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpike.class, new RenderSpike());
		 
		 RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(new ModelBullet()));
	 }
	 
	 @Override
	 public void initSounds() {}
	 
	 @Override
	 public void registerKeybindings() { KeyBindings.init(); }
	 
	 @Override
    public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {
        ClientSoundHelper.playSound(soundName, xCoord, yCoord, zCoord, volume, pitch);
    }
}
