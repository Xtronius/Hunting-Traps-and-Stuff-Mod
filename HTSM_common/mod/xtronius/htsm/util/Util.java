package mod.xtronius.htsm.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Util {
	
	public static final String UCCTS = "\u00A7";

	public static String getUnlocalizedNameInefficiently(Item item) {
        String s = item.getUnlocalizedName() + ".name";
        return s == null ? "" : StatCollector.translateToLocal(s);
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
}
