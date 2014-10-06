package mod.xtronius.htsm.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class Util {

	public static String getUnlocalizedNameInefficiently(Item item) {
        String s = item.getUnlocalizedName() + ".name";
        return s == null ? "" : StatCollector.translateToLocal(s);
    }
	
	public static String getUnwrappedUnlocalizedName(Item item) {
        return item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(".") + 1);
    }
	
	public static String getUnwrappedUnlocalizedName(String name) {
        return name.substring(name.indexOf(".") + 1);
    }
	
	public static String splitCamelCase(String s) {
	   return s.replaceAll(
	      String.format("%s|%s|%s",
	         "(?<=[A-Z])(?=[A-Z][a-z])",
	         "(?<=[^A-Z])(?=[A-Z])",
	         "(?<=[A-Za-z])(?=[^A-Za-z])"
	      ),
	      " "
	   );
	}
	
	public static void sendPlayerMessage(EntityPlayer player, String message) {
		player.addChatMessage(new ChatComponentTranslation(message));
	}
}
