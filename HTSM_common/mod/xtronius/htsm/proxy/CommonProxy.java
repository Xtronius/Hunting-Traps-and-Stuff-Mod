package mod.xtronius.htsm.proxy;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.handlers.PacketHandler;
import mod.xtronius.htsm.util.KeyBindings;
import mod.xtronius.htsm.util.list.CageList;
import mod.xtronius.htsm.util.list.ModelCageList;

public abstract class CommonProxy implements IProxy{

	public void initMiscInfo() {
		HTSM.cageList = new CageList();
		HTSM.modelCageList = new ModelCageList();
	}

	public void registerTileEntities() {}

	public void initRenderingAndTextures() {}

	public void registerEventHandlers() {}

	public void registerKeybindings() { KeyBindings.init(); }

	public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {}

	public void initPacketInfo() { PacketHandler.init(); }

	public void initSounds() {}
	
}
