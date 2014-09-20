package mod.xtronius.htsm.proxy;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.renderer.RenderItemCage;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.RenderCage;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
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
}
