package mod.xtronius.htsm.gui;

import mod.xtronius.htsm.handlers.HTSMConfigHandler;
import mod.xtronius.htsm.lib.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class ModGuiConfigs extends GuiConfig {
    public ModGuiConfigs(GuiScreen guiScreen) {
        super(guiScreen,
                new ConfigElement(HTSMConfigHandler.config.getCategory(Reference.MOD_OPTIONS)).getChildElements(),
                Reference.MOD_ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(HTSMConfigHandler.config.toString()));
    }
}
