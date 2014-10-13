package mod.xtronius.htsm.handlers;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.packet.*;
import mod.xtronius.htsm.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.relauncher.Side;

public class KeyInputHandler {
	
	Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeyBindings.keyBindingList.get(0).isPressed()) {
        	EntityPlayer player = mc.thePlayer;
        	
        	HTSM.ch.INSTANCE.sendToServer(new PacketKeyMData(player.getUniqueID().toString()));
        }
    }
}