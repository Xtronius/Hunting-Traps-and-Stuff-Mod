package mod.xtronius.htsm.proxy;

import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.RenderCage;
import net.minecraft.entity.EntityList;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	 
	 @Override
	 public void registerRenderInformation() {
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCage.class, new RenderCage());
	 }
	 
	 @Override
	 public void initSounds() {}
}
