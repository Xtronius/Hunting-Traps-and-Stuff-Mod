package mod.xtronius.htsm.packet;

import io.netty.buffer.ByteBuf;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketToggleCageGate implements IMessage{
	
	Boolean toggleGate;
	int x;
	int y;
	int z;
	
	public PacketToggleCageGate(){}
    
    public PacketToggleCageGate(boolean toggleGate, int x, int y, int z) {
        this.toggleGate = toggleGate;
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public void fromBytes(ByteBuf bytes) {
		this.toggleGate = bytes.readBoolean();
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		bytes.writeBoolean(this.toggleGate);
		bytes.writeInt(this.x);
	    bytes.writeInt(this.y);
		bytes.writeInt(this.z);
	}

	public static class Handler implements IMessageHandler<PacketToggleCageGate, IMessage> {
        
        @Override
        public IMessage onMessage(PacketToggleCageGate message, MessageContext ctx) {
        	
        	TileEntityCage tileEntity = (TileEntityCage) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        	if(tileEntity != null) {
        		tileEntity.setCageClosed(message.toggleGate);
        		
        		if (message.toggleGate) {
        		} else {
        			tileEntity.releaseEntity();
        		}
        	}
            return null;
        }
    }
}
