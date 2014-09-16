package mod.xtronius.htsm.proxy;

import java.util.HashMap;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.util.list.CageList;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
	
	public void registerRenderInformation() {}

	public void initSounds() {}
	
	public void initMiscInfo() {
		HTSM.cageList = new CageList();
	}
}
