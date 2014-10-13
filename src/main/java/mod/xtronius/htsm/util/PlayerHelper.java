package mod.xtronius.htsm.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;

public class PlayerHelper {
	public static void sendPlayerMessage(EntityPlayer player, String message) {
		player.addChatMessage(new ChatComponentTranslation(message));
	}
}
