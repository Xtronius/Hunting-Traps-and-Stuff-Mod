package mod.xtronius.htsm.item.renderer;

import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelCage;
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
public class RenderItemCage implements IItemRenderer {
	
	private static ResourceLocation texture = new ResourceLocation(Reference.MOD_Gui, "textures/modelTextureMaps/ModelCage_Texture_Map.png");
	private final ModelCage modelCage;

    public RenderItemCage() {
    	modelCage = new ModelCage();
    }

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) { return true; }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) { return true; }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack stack, Object... data) {
        switch (itemRenderType) {
            case ENTITY: {
            	renderCage(stack, 0.0F, 0.5F, 0.0F, stack.getItemDamage());
                break;
            }
            case EQUIPPED: {
            	renderCage(stack, 0.5F, 0.5F, 0.5F, stack.getItemDamage());
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
            	renderCage(stack, 1.0F, 1.0F, 0.75F, stack.getItemDamage());
                break;
            }
            case INVENTORY: {
            	renderCage(stack, 0.0F, 0.0F, 0.0F, stack.getItemDamage());
                break;
            }
            default:
                break;
        }
    }

    private void renderCage(ItemStack stack, float x, float y, float z, int metaData) {
    	
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        
        GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x, (float)y+1f, (float)z);
		GL11.glRotatef(180F, 0F, 0F, 1F);

		this.modelCage.renderModel(0.0625F);
		
//		if(tileEntity instanceof TileEntityCage) {w
//			TileEntityCage tileEntityCage = (TileEntityCage)tileEntity;
			if(stack.getTagCompound() != null && stack.getTagCompound().getTag("EntityData") != null)
				renderEntityByName(stack, x, y, z);
//		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
    }
    
    public void renderEntityByName(ItemStack stack, double x, double y, double z) {
		Entity entity = EntityList.createEntityFromNBT((NBTTagCompound) stack.getTagCompound().getTag("EntityData"), Minecraft.getMinecraft().theWorld);
		if(entity != null)
			renderEntity(entity, x, y, z);
	}
	
	public void renderEntity(Entity entity, double x, double y, double z) {

		float yOffset = (float)(0.70f);
		float var = Math.max(entity.width, entity.height);
		float scale = 0.375f/var;
		
		entity.isDead = false;
	    
	    GL11.glTranslatef(0.0F, yOffset, 0F);
	    GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
	    GL11.glTranslatef(0.0F, -yOffset, 0.0F);
	    GL11.glScalef(scale, scale, scale);
	    entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
	    RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0f);    

	}
}
