package mod.xtronius.htsm.util;

import java.awt.List;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {
    
    public static ArrayList<KeyBinding> keyBindingList = new ArrayList<KeyBinding>();
    
    public static void init() {
    	
    	keyBindingList.add(new KeyBinding("key.mode", Keyboard.KEY_M, "key.gameplay.htsm"));

    	for(KeyBinding key : keyBindingList)
    		ClientRegistry.registerKeyBinding(key);
    }

}
