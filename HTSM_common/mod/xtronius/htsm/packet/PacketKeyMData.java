package mod.xtronius.htsm.packet;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.UUID;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.item.ItemUniversalMultiTool;
import mod.xtronius.htsm.util.PlayerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketKeyMData implements IMessage{
	
	String playerName;
	
	public PacketKeyMData(){}
   
   public PacketKeyMData(String playerName) {
       this.playerName = playerName;
   }

	@Override
	public void fromBytes(ByteBuf bytes) {
		this.playerName = ByteBufUtils.readUTF8String(bytes);
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		ByteBufUtils.writeUTF8String(bytes, this.playerName);
	}

	public static class Handler implements IMessageHandler<PacketKeyMData, IMessage> {
       
       @Override
       public IMessage onMessage(PacketKeyMData message, MessageContext ctx) {
       	ArrayList<EntityPlayer> players = (ArrayList<EntityPlayer>) ctx.getServerHandler().playerEntity.worldObj.playerEntities;
       	EntityPlayer targetedPlayer = null;
       	for(EntityPlayer player : players) {
       		if(player.getUniqueID().compareTo(UUID.fromString(message.playerName)) == 0) {
       			targetedPlayer = player;
       		}
       	}
       	
       	if(targetedPlayer != null) {
       		ItemStack stack = targetedPlayer.getCurrentEquippedItem();
       		
       		if(stack != null) {
       			if(stack.getItem().equals(HTSM.itemInit.getItemByName("ItemUniversalMultiTool"))) {
       				if(stack.getItemDamage() < ItemUniversalMultiTool.modes.length-1) {
       					stack.setItemDamage(stack.getItemDamage() + 1);
       				}
       				else 
       					stack.setItemDamage(0);
       			}
       			
       			PlayerHelper.sendPlayerMessage(targetedPlayer, "Current Mode: " + ItemUniversalMultiTool.modes[stack.getItemDamage()]);
       		}
       	}
       	
           return null;
       }
   }
}

