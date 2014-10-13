package mod.xtronius.htsm.tileEntity.renderer.model;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.util.ResourceLocationHelper;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSpike
{
    private IModelCustom modelSpike;
    
    public static final String MODEL_LOCATION = "models/";

    public ModelSpike() {
    	modelSpike = AdvancedModelLoader.loadModel(ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "ModelBlockSpike.obj"));
    }

    public void render() {
    	modelSpike.renderAll();
    }
}
