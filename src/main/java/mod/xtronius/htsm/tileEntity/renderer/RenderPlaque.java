package mod.xtronius.htsm.tileEntity.renderer;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntityPlaque;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelPlaque;
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

public class RenderPlaque extends TileEntitySpecialRenderer{
	
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ASSET, "textures/modelTextureMaps/ModelPlaque_Texture_Map.png");
	public Minecraft mc = Minecraft.getMinecraft();
	
	private final ModelPlaque model;
	
	public RenderPlaque() {
		model = new ModelPlaque();
	}
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5f, (float)z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		this.bindTexture(texture);
		
		int i = tileEntity.getBlockMetadata();
					
			float rot = 0.0f;
	        if (i == 3) 
	        	rot = 180.0f;
	        if (i == 4) 
	        	rot = -90.0f;
	        if (i == 5) 
	        	rot = 90.0f;
	        
	        GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
	        
	        if(tileEntity instanceof TileEntityPlaque) { 
				TileEntityPlaque tileEntityPlaque = (TileEntityPlaque)tileEntity; 
				model.renderModel(0.0625f);
//				ItemStack stack = tileEntityPlaque.getStackInSlot(0);
				ItemStack stack = ((TileEntityPlaque) tileEntity).getStackInSlot(0);
				 
				if(stack != null) {
					EntityItem entItem = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), x, y, z, stack);
					
					entItem.hoverStart = 0.0F;
					RenderItem.renderInFrame = true;
			
					double width = entItem.width * 1.5;
					
					if(Block.getBlockFromItem(stack.getItem()) != Blocks.air)
						GL11.glTranslated(0.0, 1.25, -((1.0/16.0) * width) + (1.0/16.0) * 2.5);
					else GL11.glTranslated(0.0, 1.25, -((1.0/16.0) * width) + (1.0/16.0) * 6.0);
					GL11.glRotatef(-180, 0, 1, 0);
					GL11.glRotatef(180, 0, 0, 1);
					GL11.glScalef(1.50f, 1.50f, 1.50f);
					
					RenderManager.instance.renderEntityWithPosYaw(entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
					RenderItem.renderInFrame = false;
				}
			}
		
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}

