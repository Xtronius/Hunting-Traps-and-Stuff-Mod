package mod.xtronius.htsm.packet;

import java.io.IOException;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class PacketCageData implements IMessage{
	
	String entityID;
	int x;
	int y;
	int z;
	
	public PacketCageData(){}
    
    public PacketCageData(String entityID, int x, int y, int z) {
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public void fromBytes(ByteBuf bytes) {
		this.entityID = ByteBufUtils.readUTF8String(bytes);
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		ByteBufUtils.writeUTF8String(bytes, this.entityID);
		bytes.writeInt(this.x);
	    bytes.writeInt(this.y);
		bytes.writeInt(this.z);
	}

	public static class Handler implements IMessageHandler<PacketCageData, IMessage> {
        
        @Override
        public IMessage onMessage(PacketCageData message, MessageContext ctx) {
        	
        	TileEntityCage tileEntity = (TileEntityCage) Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        	if(tileEntity != null && message.entityID != null) {
        		tileEntity.targetEntityID = message.entityID;
        	} else System.out.println("FAILED!!!");
            return null;
        }
    }
}
