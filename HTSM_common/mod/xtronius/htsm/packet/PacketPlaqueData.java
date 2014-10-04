 package mod.xtronius.htsm.packet;

import io.netty.buffer.ByteBuf;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketPlaqueData implements IMessage{
	
	ItemStack stack;
	int x;
	int y;
	int z;
	
	public PacketPlaqueData(){}
    
    public PacketPlaqueData(ItemStack stack, int x, int y, int z) {
        this.stack = stack;
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public void fromBytes(ByteBuf bytes) {
		this.stack = ByteBufUtils.readItemStack(bytes);
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		ByteBufUtils.writeItemStack(bytes, this.stack);
		bytes.writeInt(this.x);
	    bytes.writeInt(this.y);
		bytes.writeInt(this.z);
	}

	public static class Handler implements IMessageHandler<PacketPlaqueData, IMessage> {
        
        @Override
        public IMessage onMessage(PacketPlaqueData message, MessageContext ctx) {
        	
        	TileEntityPlaque tileEntity = (TileEntityPlaque) Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        	if(tileEntity != null) {
        		tileEntity.setInventorySlotContents(0, message.stack);
        	}
        	
            return null;
        }
    }
}
