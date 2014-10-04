 package mod.xtronius.htsm.packet;

import io.netty.buffer.ByteBuf;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSpikeData implements IMessage{
	
	byte orientation;
	int x;
	int y;
	int z;
	
	public PacketSpikeData(){}
    
    public PacketSpikeData(byte orientation, int x, int y, int z) {
        this.orientation = orientation;
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public void fromBytes(ByteBuf bytes) {
		this.orientation = bytes.readByte();
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		bytes.writeByte(this.orientation);
		bytes.writeInt(this.x);
	    bytes.writeInt(this.y);
		bytes.writeInt(this.z);
	}

	public static class Handler implements IMessageHandler<PacketSpikeData, IMessage> {
        
        @Override
        public IMessage onMessage(PacketSpikeData message, MessageContext ctx) {
        	
        	TileEntitySpike tileEntity = (TileEntitySpike) Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        	if(tileEntity != null) {
        		tileEntity.setOrientation(message.orientation);
        	}
            return null;
        }
    }
}
