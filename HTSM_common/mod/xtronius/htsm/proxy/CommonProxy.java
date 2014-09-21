package mod.xtronius.htsm.proxy;

import java.util.HashMap;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.util.list.CageList;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IProxy{
	
	public void registerRenderInformation() {}

	public void initSounds() {}
	
	public void initMiscInfo() {
		HTSM.cageList = new CageList();
	}

	@Override
	public void registerTileEntities() {}

	@Override
	public void initRenderingAndTextures() {}

	@Override
	public void registerEventHandlers() {}

	@Override
	public void registerKeybindings() {}

	@Override
	public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {}
}
