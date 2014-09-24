 package mod.xtronius.htsm.packet;

import io.netty.buffer.ByteBuf;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCageData implements IMessage{
	
	String entityID;
	NBTTagCompound nbt;
	boolean isGateClosed;
	int item;
	int x;
	int y;
	int z;
	
	public PacketCageData(){}
    
    public PacketCageData(String entityID, NBTTagCompound nbt, boolean isGateClosed, int item, int x, int y, int z) {
        this.entityID = entityID;
        this.nbt = nbt;
        this.isGateClosed = isGateClosed;
        this.item = item;
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public void fromBytes(ByteBuf bytes) {
		this.entityID = ByteBufUtils.readUTF8String(bytes);
		this.nbt = ByteBufUtils.readTag(bytes);
		this.isGateClosed = bytes.readBoolean();
		this.item = bytes.readInt();
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		ByteBufUtils.writeUTF8String(bytes, this.entityID);
		ByteBufUtils.writeTag(bytes, this.nbt);
		bytes.writeBoolean(isGateClosed);
		bytes.writeInt(this.item);
		bytes.writeInt(this.x);
	    bytes.writeInt(this.y);
		bytes.writeInt(this.z);
	}

	public static class Handler implements IMessageHandler<PacketCageData, IMessage> {
        
        @Override
        public IMessage onMessage(PacketCageData message, MessageContext ctx) {
        	
        	TileEntityCage tileEntity = (TileEntityCage) Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        	if(tileEntity != null) {
        		tileEntity.targetEntityID = message.entityID;
        		tileEntity.setEntityData(message.nbt);
        		tileEntity.setCageClosed(message.isGateClosed);
        		tileEntity.item = message.item;
        	}
            return null;
        }
    }
}
