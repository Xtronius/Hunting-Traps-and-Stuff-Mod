package mod.xtronius.htsm.proxy;

import mod.xtronius.htsm.core.HTSM;
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
		HTSM.ch.addChannel("packetCageData").registerMessage(PacketCageData.Handler.class, PacketCageData.class, 0, Side.CLIENT);
		HTSM.ch.addChannel("packetToggleCageGate").registerMessage(PacketToggleCageGate.Handler.class, PacketToggleCageGate.class, 0, Side.SERVER);
		HTSM.ch.addChannel("packetPlaqueData").registerMessage(PacketPlaqueData.Handler.class, PacketPlaqueData.class, 0, Side.CLIENT);
//	 MyMod.network.sendToServer(new MyMessage("foobar"));
//	 MyMod.network.sendTo(new SomeMessage(), somePlayer);
	}

	public void initSounds() {}
	
}
