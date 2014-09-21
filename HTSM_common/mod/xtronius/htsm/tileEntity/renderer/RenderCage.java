package mod.xtronius.htsm.tileEntity.renderer;

import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelCage;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class RenderCage extends TileEntitySpecialRenderer{
	
	
	public ModelCage model;
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ASSET, "textures/modelTextureMaps/ModelCage_Texture_Map.png");
	public Minecraft mc = Minecraft.getMinecraft();
	
	public RenderCage() {
		this.model = new ModelCage();
	}
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5f, (float)z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);

		this.bindTexture(texture);
		this.model.renderModel(0.0625F);
		
		if(tileEntity instanceof TileEntityCage) {
			TileEntityCage tileEntityCage = (TileEntityCage)tileEntity;
			if(tileEntityCage.getEntityData() != null)
				renderEntityByName(tileEntityCage, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	public void renderEntityByName(TileEntityCage tileEntity, double x, double y, double z) {
		Entity entity = EntityList.createEntityFromNBT(tileEntity.getEntityData(), this.mc.theWorld);
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

