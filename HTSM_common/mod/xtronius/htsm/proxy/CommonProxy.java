package mod.xtronius.htsm.proxy;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.handlers.PacketHandler;
import mod.xtronius.htsm.packet.PacketCageData;
import mod.xtronius.htsm.packet.PacketPlaqueData;
import mod.xtronius.htsm.packet.PacketToggleCageGate;
import mod.xtronius.htsm.util.list.CageList;
import mod.xtronius.htsm.util.list.ModelCageList;
import cpw.mods.fml.relauncher.Side;

public abstract class CommonProxy implements IProxy{

	public void initMiscInfo() {
		HTSM.cageList = new CageList();
		HTSM.modelCageList = new ModelCageList();
	}

	public void registerTileEntities() {}

	public void initRenderingAndTextures() {}

	public void registerEventHandlers() {}

	public void registerKeybindings() {}

	public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {}

	public void initPacketInfo() {
		PacketHandler.init();
	}

	public void initSounds() {}
	
}
