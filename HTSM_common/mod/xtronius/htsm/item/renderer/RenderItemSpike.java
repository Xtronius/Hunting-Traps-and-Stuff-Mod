package mod.xtronius.htsm.item.renderer;

import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.RenderSpike;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelSpike;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderItemSpike implements IItemRenderer {
	
	private final ModelSpike modelSpike;
	private ResourceLocation texture = new ResourceLocation(Reference.MOD_ASSET, "textures/modelTextureMaps/ModelBlockSpike_Texture_Map_2.png");

    public RenderItemSpike() {
    	modelSpike = new ModelSpike();
    }

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) { return true; }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) { return true; }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack stack, Object... data) {
        switch (itemRenderType) {
            case ENTITY: {
            	renderSpike(stack, stack.getItemDamage(), -1f, 0.0F, -1F, 0.0F, 0.0F, 0.0F, 0.4375F, 0.4375F, 0.4375F);
                break;
            }
            case EQUIPPED: {
            	renderSpike(stack, stack.getItemDamage(), 0.5F, 0.5F, 0.5F, 0.0F, 1.0F, 0.0F, 0.4375F, 0.4375F, 0.4375F);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
            	renderSpike(stack, stack.getItemDamage(), 0.0F, 0.0F, 0.71875F, 0.0F, 0.75F, 0.0F, 0.4375F, 0.4375F, 0.4375F);
                break;
            }
            case INVENTORY: {
            	renderSpike(stack, stack.getItemDamage(), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F);
                break;
            }
            default:
                break;
        }
    }
    
    private void renderSpike(ItemStack stack, int meta, float posX, float posY, float posZ) {
    	renderSpike(stack, meta, posX, posY, posZ, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderSpike(ItemStack stack, int meta, float posX, float posY, float posZ, float transX, float transY, float transZ, float scaleX, float scaleY, float scaleZ) {
    	
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPushMatrix();
        GL11.glScalef(scaleX, scaleY, scaleZ);
		GL11.glTranslatef((float)posX + transX, (float)posY + transY, (float)posZ + transZ);
//		GL11.glRotatef(180F, 0F, 0F, 1F);

		this.modelSpike.render();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
    }
}
