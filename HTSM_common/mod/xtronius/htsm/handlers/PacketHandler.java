package mod.xtronius.htsm.handlers;

import java.util.HashMap;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.packet.PacketCageData;
import mod.xtronius.htsm.packet.PacketPlaqueData;
import mod.xtronius.htsm.packet.PacketSpikeData;
import mod.xtronius.htsm.packet.PacketToggleCageGate;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init() {
    	INSTANCE.registerMessage(PacketCageData.Handler.class, PacketCageData.class, 0, Side.CLIENT);
    	INSTANCE.registerMessage(PacketToggleCageGate.Handler.class, PacketToggleCageGate.class, 1, Side.SERVER);
    	INSTANCE.registerMessage(PacketPlaqueData.Handler.class, PacketPlaqueData.class, 2, Side.CLIENT);
    	INSTANCE.registerMessage(PacketSpikeData.Handler.class, PacketSpikeData.class, 3, Side.CLIENT);
//	 MyMod.network.sendToServer(new MyMessage("foobar"));
//	 MyMod.network.sendTo(new SomeMessage(), somePlayer);
    }
}
