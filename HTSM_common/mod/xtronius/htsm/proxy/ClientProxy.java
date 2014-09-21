package mod.xtronius.htsm.proxy;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.renderer.RenderItemCage;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.RenderCage;
import mod.xtronius.htsm.util.ClientSoundHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	 
	 @Override
	 public void registerRenderInformation() {
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCage.class, new RenderCage());
		 MinecraftForgeClient.registerItemRenderer(HTSM.itemInit.getItemByName("ItemCage"), new RenderItemCage());
	 }
	 
	 @Override
	 public void initSounds() {}
	 
	 @Override
    public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {
        ClientSoundHelper.playSound(soundName, xCoord, yCoord, zCoord, volume, pitch);
    }
}
