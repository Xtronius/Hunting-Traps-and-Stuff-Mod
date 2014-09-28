package mod.xtronius.htsm.proxy;

import cpw.mods.fml.relauncher.Side;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.packet.PacketCageData;
import mod.xtronius.htsm.packet.PacketToggleCageGate;
import mod.xtronius.htsm.util.list.CageList;
import mod.xtronius.htsm.util.list.ModelCageList;

public class ServerProxy extends CommonProxy{

	@Override
	public void registerTileEntities() {}

	@Override
	public void registerEventHandlers() {}

	@Override
	public void initMiscInfo() {}

	@Override
	public void initRenderingAndTextures() {}

	@Override
	public void initSounds() {}

	@Override
	public void registerKeybindings() {}

	@Override
	public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {}

	@Override
	public void initPacketInfo() {}
}
