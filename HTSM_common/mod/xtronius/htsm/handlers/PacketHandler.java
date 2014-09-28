package mod.xtronius.htsm.handlers;

import java.util.HashMap;

import mod.xtronius.htsm.packet.PacketCageData;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
	private HashMap<String, SimpleNetworkWrapper> channels = new HashMap<String, SimpleNetworkWrapper>();

	public SimpleNetworkWrapper getChannel(String channelID) { return channels.get(channelID); }
	public SimpleNetworkWrapper addChannel(String channelID) {
		channels.put(channelID, NetworkRegistry.INSTANCE.newSimpleChannel(channelID)); 
		return getChannel(channelID);
	}
}
