package mod.xtronius.htsm.util;

import mod.xtronius.htsm.lib.Reference;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper {
    public static ResourceLocation getResourceLocation(String modId, String path) {
        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path) {
        return getResourceLocation(Reference.MOD_ASSET.toLowerCase(), path);
    }
}
