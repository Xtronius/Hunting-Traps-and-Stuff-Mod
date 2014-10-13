package mod.xtronius.htsm.tileEntity.renderer;

import mod.xtronius.htsm.lib.ConfigValues;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelCage;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class RenderCage extends TileEntitySpecialRenderer{
	
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ASSET, "textures/modelTextureMaps/ModelCage_Texture_Map.png");
	public Minecraft mc = Minecraft.getMinecraft();
	
	public RenderCage() {}
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5f, (float)z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);

		if(tileEntity instanceof TileEntityCage) {
			TileEntityCage tileEntityCage = (TileEntityCage)tileEntity;
		
		
		this.bindTexture(texture);
		
		if(ConfigValues.RenderCageGateAnimation) {
		
			if(!tileEntityCage.isCageClosed()) {
				this.openCageGate(tileEntityCage.model, tileEntityCage.model.GateBackTop);
				this.openCageGate(tileEntityCage.model, tileEntityCage.model.GateFrontTop);
				this.openCageGate(tileEntityCage.model, tileEntityCage.model.GateLeftTop);
				this.openCageGate(tileEntityCage.model, tileEntityCage.model.GateRightTop);		
			} else {
				this.closeCageGate(tileEntityCage.model.GateBackTop);
				this.closeCageGate(tileEntityCage.model.GateFrontTop);
				this.closeCageGate(tileEntityCage.model.GateLeftTop);
				this.closeCageGate(tileEntityCage.model.GateRightTop);
			}
		}
		
		
			tileEntityCage.model.renderModel(0.0625F);
		
		
			if(tileEntityCage.getEntityData() != null) {
				renderEntityByName(tileEntityCage, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			} else {
				ItemStack displayStack = tileEntityCage.displayStack;
				
				if(displayStack != null) {
					EntityItem entItem = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), x, y, z, displayStack);
					
					entItem.hoverStart = 0.0F;
					RenderItem.renderInFrame = true;
			
					GL11.glTranslated(0.0f, 1.375F, 0.0f);
					if(ConfigValues.RenderCageEntityRotationAnimation)
						GL11.glRotatef((Sys.getTime()%188743680)/10, 0, 1, 0);
					GL11.glRotatef(180, 0, 0, 1);
					GL11.glScalef(1.0f, 1.0f, 1.0f);
					
					RenderManager.instance.renderEntityWithPosYaw(entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
					RenderItem.renderInFrame = false;
				}
			}
		}
		
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private void closeCageGate(ModelRenderer part) {
		if(part.offsetX > 0 ) part.offsetX -= 0.025f;
		if(part.offsetY > 0 ) part.offsetY -= 0.025f;
		if(part.offsetZ > 0 ) part.offsetZ -= 0.025f;
		
		if(part.offsetX < 0 ) part.offsetX += 0.025f;
		if(part.offsetY < 0 ) part.offsetY += 0.025f;
		if(part.offsetZ < 0 ) part.offsetZ += 0.025f;
		
		if(part.rotateAngleX > 0 ) part.rotateAngleX -= 0.025f;
		if(part.rotateAngleY > 0 ) part.rotateAngleY -= 0.025f;
		if(part.rotateAngleZ > 0 ) part.rotateAngleZ -= 0.025f;
		
		if(part.rotateAngleX < 0 ) part.rotateAngleX += 0.025f;
		if(part.rotateAngleY < 0 ) part.rotateAngleY += 0.025f;
		if(part.rotateAngleZ < 0 ) part.rotateAngleZ += 0.025f;
	}
	
	private void openCageGate(ModelCage model, ModelRenderer part) {
		
		if(part.equals(model.GateBackTop)) {
			if(part.rotateAngleX < 24f/16F) part.rotateAngleX += 0.025f;
			if(part.offsetY < 0.5f/16f) part.offsetY += 0.025f;
		}
		
		if(part.equals(model.GateFrontTop)) {
			if(part.offsetZ < 1.0f/16f) part.offsetZ += 0.01f;
			if(part.rotateAngleX > -24f/16F) part.rotateAngleX -= 0.025f;
		}

		if(part.equals(model.GateLeftTop)) {
			if(part.rotateAngleZ > -24f/16F) part.rotateAngleZ -= 0.025f;
			if(part.offsetY < 0.5f/16f) part.offsetY += 0.025f;
		}

		if(part.equals(model.GateRightTop)) {
			if(part.offsetX < 1.0f/16f) part.offsetX += 0.01f;
			if(part.rotateAngleZ < 24f/16F) part.rotateAngleZ += 0.025f;
			
		}
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
	    
	    GL11.glTranslatef(0.0F, yOffset, 0F);
	    GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
	    GL11.glRotatef((Sys.getTime()%188743680)/10, 0, 1, 0);
	    GL11.glTranslatef(0.0F, -yOffset, 0.0F);
	    GL11.glScalef(scale, scale, scale);
	    entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
	    RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0f);    

	}
}

